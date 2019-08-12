package com.tna.yb_store.controller;

import cn.yiban.open.common.User;
import cn.yiban.util.HTTPSimple;
import com.tna.yb_store.entity.BaseResponseBody;
import com.tna.yb_store.entity.Order;
import com.tna.yb_store.entity.UserResponseBody;
import com.tna.yb_store.entity.YbUser;
import com.tna.yb_store.mapper.OrderMapper;
import com.tna.yb_store.mapper.ProductMapper;
import com.tna.yb_store.service.ManagerService;
import com.tna.yb_store.utils.AppContext;
import com.tna.yb_store.utils.MessageXsendUtils;
import com.tna.yb_store.utils.redis.RedisUtil;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

/**
 * 用户API
 * /v1/user/me             GET        获取当前用户基本信息。
 * /v1/user/real_me        GET         获取当前用户实名信息。
 * /v1/user/verify_me      GET         获取当前用户校方认证信息。
 * /v1/user/is_real        GET      指定用户是否实名认证。
 * /v1/user/is_verify      GET     当前用户是否校方认证
 * /v1/user/check_verify   POST（form-data方式）当前用户完成校方认证
 * /v1/user/pay/{phone_number,order} POST
 * /v1/user/trade/{order_id,conversion_code}  GET
 */
@RequestMapping("/v1/user")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private Logger logger = LogManager.getLogger(UserController.class.getName());
    private final RedisUtil redisUtil;
    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;
    private final ManagerService managerService;
    private Random ran = new Random();

    @Autowired
    public UserController(RedisUtil redisUtil, OrderMapper orderMapper, ProductMapper productMapper, ManagerService managerService) {
        this.redisUtil = redisUtil;
        this.orderMapper = orderMapper;
        this.productMapper = productMapper;
        this.managerService = managerService;
    }

    @PostMapping("/pay")
    public HttpEntity<?> pay(
            @RequestHeader(name = "X-Token") String user_id, String phone_number, Order order) {
        BaseResponseBody baseResponseBody = BaseResponseBody.getInstance();
        String url = AppContext.PAY_API
                + "?access_token="
                + redisUtil.get(user_id)
                + "&pay="
                + order.getYb_money()
                + "&sign_back="
                + "http://192.168.43.200:8080/v1/user/trade";
        if (redisUtil.checkToken(user_id)) {
            logger.info("接收用户"+user_id+"的支付请求！");
            if (productMapper.selectProductNumberById(order.getProduct_id()) > 0 ) {
                logger.info("查询到"+user_id+"的产品需求！");
                if (Integer.parseInt(order.getYb_money()) > 0) {
                    logger.info("用户"+user_id+"开始支付！");
                    String payInfo = HTTPSimple.GET(url);
                    JSONObject jsonObject = JSONObject.fromObject(payInfo);
                    logger.info("用户"+user_id+"的支付信息："+jsonObject);
                    if (jsonObject.getString("status").equals("success")) {
                        String conversion_code = getConversionCode();
                        String user_product_id = user_id+"-"+order.getProduct_id();
                        if (redisUtil.get(user_product_id)!=null){
                            user_product_id=user_product_id+"+";
                            redisUtil.set(user_product_id,conversion_code);
                        }else {
                            redisUtil.set(user_product_id,conversion_code);
                        }
                        logger.info("用户"+user_id+"支付成功！--"+user_product_id+"-"+conversion_code);
                        if (orderMapper.insertOrder(order) > 0) {
                            logger.info("用户"+user_id+"的订单已生成！");
                            if (productMapper.updateProductNumber(order.getProduct_id()) > 0){
                                logger.info("成功更新用户"+user_id+"所买的产品数量！");
                                if (sendMessage(phone_number,conversion_code)) {
                                    String key = user_id + "-" + order.getId();
                                    redisUtil.set(key,conversion_code);
                                    redisUtil.remove(user_product_id);
                                    logger.info("成功发送用户"+user_id+"的兑换码！");
                                    baseResponseBody.setCode(0);
                                    baseResponseBody.setMsg("购买成功，兑换码已发送！");
                                } else {
                                    baseResponseBody.setCode(1);
                                    baseResponseBody.setMsg("短信发送失败！请联系管理员。");
                                    logger.error("用户"+user_id+"的兑换码发送失败！");
                                }
                            }else {
                                baseResponseBody.setCode(1);
                                baseResponseBody.setMsg("数据库更新错误！请联系管理员。");
                                logger.error("更新用户"+user_id+"所买的产品数量失败！");
                            }
                        } else {
                            baseResponseBody.setCode(1);
                            baseResponseBody.setMsg("数据库订单生成错误！请联系管理员。");
                            logger.error("生成用户"+user_id+"的订单失败！");
                        }
                    } else {
                        baseResponseBody.setCode(1);
                        baseResponseBody.setMsg("支付失败！" + jsonObject.getJSONObject("info").getString("msgCN"));
                        logger.error("用户"+user_id+"支付失败--"+ jsonObject.getJSONObject("info").getString("msgCN"));
                    }
                } else {
                    baseResponseBody.setCode(1);
                    baseResponseBody.setMsg("支付网薪不能为者负数！");
                    logger.error("用户"+user_id+"交易失败--支付网薪数量错误，不能为0或负数！");
                }
            } else {
                baseResponseBody.setCode(1);
                logger.error("用户"+user_id+"交易失败，该商品已售完！");
                baseResponseBody.setMsg("交易失败--该商品已售完！");
            }
        } else {
            baseResponseBody.setCode(-1);
            baseResponseBody.setMsg("你还没有授权噢！");
            logger.error("用户"+user_id+"交易失败--未授权！");
        }
        return new ResponseEntity<BaseResponseBody>(baseResponseBody, HttpStatus.OK);
    }

    @GetMapping("/trade")
    public HttpEntity<?> trade(
            @RequestHeader(name = "X-Token") String user_id, int order_id, String conversion_code) {
        BaseResponseBody baseResponseBody = BaseResponseBody.getInstance();
        if (redisUtil.checkToken(user_id)) {
            logger.info("接收"+user_id+"的兑换请求！");
            String[] conversion_codes = conversion_code.split("-");
            if (conversion_codes.length == 2) {
                logger.info("用户"+user_id+"的输入格式正确！");
                if (conversion_codes[1].equals(managerService.checkManagerId(conversion_codes[1]))) {
                    logger.info("用户"+user_id+"的兑换人学号存在！");
                    String user_order = String.valueOf(redisUtil.get(user_id + "-" + order_id));
                    if (user_order.equals(conversion_codes[0])) {
                        logger.info("用户"+user_id+"的兑换码正确！");
                        if (orderMapper.updateStatuByOrderId(1, conversion_codes[1], order_id) > 0) {
                            baseResponseBody.setCode(0);
                            baseResponseBody.setMsg("兑换成功！");
                            logger.info("用户"+user_id+"兑换成功！");
                            redisUtil.remove(user_id+"-"+order_id);
                        } else {
                            baseResponseBody.setCode(1);
                            baseResponseBody.setMsg("兑换失败！请稍后重试");
                            logger.error("用户"+user_id+"兑换失败--没有改变订单状态！");
                        }
                    } else {
                        baseResponseBody.setCode(1);
                        baseResponseBody.setMsg("请检查你输入的验证码是否正确！");
                        logger.error("用户"+user_id+"兑换失败--验证码错误！");
                    }
                } else {
                    baseResponseBody.setCode(1);
                    baseResponseBody.setMsg("请检查管理员的学号是否正确！若未完成校方认证请易班官网完成认证");
                    logger.error("用户"+user_id+"兑换失败--管理员的学号错误或者未完成校方认证！");
                }
            } else {
                baseResponseBody.setCode(1);
                baseResponseBody.setMsg("请检查你输入的验证格式是否正确！");
                logger.error("用户"+user_id+"兑换失败--验证格式错误！");
            }
        } else {
            baseResponseBody.setCode(-1);
            baseResponseBody.setMsg("你还没有授权噢！");
            logger.error("用户"+user_id+"兑换失败--未授权！");
        }
        return new ResponseEntity<BaseResponseBody>(baseResponseBody, HttpStatus.OK);
    }


    @GetMapping("/me")
    public HttpEntity<?> getUserMe(@RequestHeader(name = "X-Token") String user_id) {
        UserResponseBody userResponseBody = new UserResponseBody();
        if (redisUtil.checkToken(user_id)) {
            User user = new User((String) redisUtil.get(user_id));
            YbUser ybUser = new YbUser();
            JSONObject userInfo = JSONObject.fromObject(user.me()).getJSONObject("info");
            JSONObject otherUserInfo = JSONObject.fromObject(user.other(2));

            ybUser.setYb_userid(userInfo.getString("yb_userid"));
            ybUser.setYb_username(userInfo.getString("yb_username"));
            ybUser.setYb_usernick(userInfo.getString("yb_usernick"));
            ybUser.setYb_sex(userInfo.getString("yb_sex"));
            ybUser.setYb_money(userInfo.getString("yb_money"));
            ybUser.setYb_exp(userInfo.getString("yb_exp"));
            ybUser.setYb_userhead(userInfo.getString("yb_userhead"));
            ybUser.setYb_schoolname(userInfo.getString("yb_schoolname"));
            ybUser.setYb_schoolid(userInfo.getString("yb_schoolid"));
            ybUser.setYb_regtime(userInfo.getString("yb_regtime"));

            userResponseBody.setCode(0);
            userResponseBody.setMsg("成功获取用户信息！");
            logger.info("成功获取"+user_id+"的用户信息！");
            userResponseBody.setYbUser(ybUser);
        } else {
            userResponseBody.setCode(-1);
            userResponseBody.setMsg("你还没有授权噢！");
            logger.error("获取用户"+user_id+"的信息失败--未授权！");
        }
        return new ResponseEntity<UserResponseBody>(userResponseBody, HttpStatus.OK);
    }

    private String getConversionCode() {
        String conversion_code = "";
        for (int i = 0; i < 6; i++) {
            conversion_code = conversion_code + ran.nextInt(10);
        }
        return conversion_code;
    }

    private boolean getUser_Other(String student_id, String user_id) {
        boolean flag = false;
        String url = AppContext.OTHER_API
                + "?access_token=" + redisUtil.get(user_id)
                + "&school_name=贵州民族大学&"
                + "student_id=" + student_id;
        String result = HTTPSimple.GET(url);
        JSONObject jsonObject = JSONObject.fromObject(result);
        System.out.println(jsonObject);
        if (jsonObject.getString("status").equals("success")) {
            flag = true;
        }
        return flag;
    }

    private boolean sendMessage(String phone_number, String conversion_code) {
        boolean flag = false;
        JSONObject vars = new JSONObject();
        String to = null;
        to = phone_number;
        if (to != null) {
            vars.put("code", conversion_code);
            flag = MessageXsendUtils.send(AppContext.APPID_SML, AppContext.APPKEY_SML, to, AppContext.PROJECT_SML, vars);
        }
        return flag;
    }
}
