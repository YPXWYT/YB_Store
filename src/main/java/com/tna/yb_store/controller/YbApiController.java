package com.tna.yb_store.controller;

import cn.yiban.open.Authorize;
import cn.yiban.open.common.User;
import com.submail.config.AppConfig;
import com.submail.lib.MESSAGEXsend;
import com.submail.utils.ConfigLoader;
import com.tna.yb_store.entity.BaseResponseBody;
import com.tna.yb_store.entity.Product;
import com.tna.yb_store.entity.TokenResponseBody;
import com.tna.yb_store.entity.YbUser;
import com.tna.yb_store.service.ProductService;
import com.tna.yb_store.utils.AppContext;
import com.tna.yb_store.utils.RandomUtils;
import com.tna.yb_store.utils.redis.RedisUtil;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/v1")
public class YbApiController {

    @Resource
    private RedisUtil redisUtil;

    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Resource
    private ProductService productService;


    @RequestMapping("/authorize")
    public void getCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authorize authorize = new Authorize(AppContext.APPID, AppContext.APPSECRET);
        String url = authorize.forwardurl(AppContext.BACKURL, "QUERY", Authorize.DISPLAY_TAG_T.WEB);
        response.sendRedirect(url);
    }

    @RequestMapping("/access_token")
    public void getToken1(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String msg, status;
//        String url = "http://127.0.0.1:3000";
        String code = request.getParameter("code");
        TokenResponseBody tokenResponseBody = new TokenResponseBody();

        if (code == null || code.isEmpty()) {
            msg = "code为null,授权信息错误！";
            status = "error";
            BaseResponseBody baseResponseBody = new BaseResponseBody();
            baseResponseBody.setMessage(msg);
            baseResponseBody.setStatus(status);
            request.setAttribute("info", baseResponseBody);
            request.getRequestDispatcher("error.html").forward(request, response);
        }

        Authorize auth = new Authorize(AppContext.APPID, AppContext.APPSECRET);
        System.out.println(auth);
        String queryTokenRS = auth.querytoken(code, AppContext.BACKURL);


        if (queryTokenRS == null || queryTokenRS.isEmpty()) {
            msg = "queryTokenRS为null,授权信息错误！";
            status = "error";
            BaseResponseBody baseResponseBody = new BaseResponseBody();
            baseResponseBody.setMessage(msg);
            baseResponseBody.setStatus(status);
            request.setAttribute("info", baseResponseBody);
            request.getRequestDispatcher("error.html").forward(request, response);
        }
        JSONObject json = JSONObject.fromObject(queryTokenRS);

        if (json == null) {
            msg = "json为null,json转换错误！";
            status = "error";
            BaseResponseBody baseResponseBody = new BaseResponseBody();
            baseResponseBody.setMessage(msg);
            baseResponseBody.setStatus(status);
            request.setAttribute("info", baseResponseBody);
            request.getRequestDispatcher("error.html").forward(request, response);
        }

        if (Objects.requireNonNull(json).has("access_token")) {

            String accessToken = json.getString("access_token");
            String accessToken_key = UUID.randomUUID().toString();
            redisUtil.set(accessToken_key, accessToken,60*60L);
            //授权查询
            String text1 = auth.getManInstance(accessToken).query();
            System.out.println(text1);

            User user = new User(accessToken);
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

            List<Product> products = productService.findProductAll();
            tokenResponseBody.setAccessToken_key(accessToken_key);
            tokenResponseBody.setUser(ybUser);
            tokenResponseBody.setInfo(products);
            tokenResponseBody.setMessage("成功获取用户信信息、授权信息及产品信息");
            tokenResponseBody.setStatus("success");
            request.setAttribute("info", tokenResponseBody);

            System.out.println("成功获取用户信信息、授权信息及产品信息");
//            redisUtil.set("pList",products,10*1000L);
//            request.getRequestDispatcher("ok.html").forward(request, response);
        } else {
            msg = "获取token信息错误！";
            status = "error";
            BaseResponseBody baseResponseBody = new BaseResponseBody();
            baseResponseBody.setMessage(msg);
            baseResponseBody.setStatus(status);
            request.setAttribute("info", baseResponseBody);
            request.getRequestDispatcher("error.html").forward(request, response);
        }
    }
    @RequestMapping("/sendMessage")
    public boolean sendMessage(HttpServletRequest request,HttpServletResponse response){
        boolean flag = false;
        AppConfig config = ConfigLoader.createConfig("37931","d80c2ce89365f9bc2fe941954ae3409d","nomal");
//        AppConfig config = ConfigLoader.createConfig("37887","5605e320e9073a46c5566e7fe7b378d1","nomal");
        MESSAGEXsend submail = new MESSAGEXsend(config);

        AppContext.CONVERSION_CODE = RandomUtils.getConversionCode();
        submail.addTo("18744972966");
        submail.setProject("wII4e");
        submail.addVar("code", AppContext.CONVERSION_CODE);

        String result=null;
        try {
            result=submail.xsend();;
            System.out.println(result);
            JSONObject jsonObject = JSONObject.fromObject(result);
            result = jsonObject.getString("status");
            System.out.println(result);

            if (result.equals("success")){
                redisUtil.set("conversion_code",AppContext.CONVERSION_CODE,10*1000L);
                flag=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
