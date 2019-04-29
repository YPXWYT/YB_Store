package com.tna.yb_store.controller;

import cn.yiban.open.Authorize;
import cn.yiban.open.common.User;
import com.tna.yb_store.entity.BaseResponseBody;
import com.tna.yb_store.entity.Product;
import com.tna.yb_store.entity.TokenResponseBody;
import com.tna.yb_store.entity.YbUser;
import com.tna.yb_store.service.ProductService;
import com.tna.yb_store.utils.AppContext;
import com.tna.yb_store.utils.redis.RedisUtil;
import net.sf.json.JSONObject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

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

    //    @RequestMapping("/access_token")
    @Cacheable("productCache")
    public ResponseEntity<?> getToken(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String msg, status;
        String code = request.getParameter("code");
        TokenResponseBody tokenResponseBody = new TokenResponseBody();

        if (code == null || code.isEmpty()) {
            msg = "code为null,授权信息错误！";
            status = "error";
            BaseResponseBody baseResponseBody = new BaseResponseBody();
            baseResponseBody.setMessage(msg);
            baseResponseBody.setStatus(status);
            return ResponseEntity.ok(baseResponseBody);
        }

        Authorize auth = new Authorize(AppContext.APPID, AppContext.APPSECRET);
        String queryTokenRS = auth.querytoken(code, AppContext.BACKURL);


        if (queryTokenRS == null || queryTokenRS.isEmpty()) {
            msg = "queryTokenRS为null,授权信息错误！";
            status = "error";
            BaseResponseBody baseResponseBody = new BaseResponseBody();
            baseResponseBody.setMessage(msg);
            baseResponseBody.setStatus(status);
            return ResponseEntity.ok(baseResponseBody);
        }
        JSONObject json = JSONObject.fromObject(queryTokenRS);

        if (json == null) {
            msg = "json为null,json转换错误！";
            status = "error";
            BaseResponseBody baseResponseBody = new BaseResponseBody();
            baseResponseBody.setMessage(msg);
            baseResponseBody.setStatus(status);
            return ResponseEntity.ok(baseResponseBody);
        }

        if (Objects.requireNonNull(json).has("access_token")) {

            String accessToken = json.getString("access_token");
            redisUtil.set("access_token", accessToken);

            String url = "http://127.0.0.1:3000?token=" +
                    accessToken;
            response.sendRedirect(url);
            request.getRequestDispatcher(url).forward(request, response);
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
            tokenResponseBody.setToken(accessToken);
            tokenResponseBody.setUser(ybUser);
            tokenResponseBody.setInfo(products);
            tokenResponseBody.setMessage("成功获取用户信信息、授权信息及产品信息");
            tokenResponseBody.setStatus("success");
        } else {
            msg = "获取token信息错误！";
            status = "error";
            BaseResponseBody baseResponseBody = new BaseResponseBody();
            baseResponseBody.setMessage(msg);
            baseResponseBody.setStatus(status);
            return ResponseEntity.ok(baseResponseBody);
        }
        return ResponseEntity.ok(tokenResponseBody);
    }

    @RequestMapping("/access_token")
    public void getToken1(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String msg, status;
        String url = "http://127.0.0.1:3000";
        String code = request.getParameter("code");
        TokenResponseBody tokenResponseBody = new TokenResponseBody();

        if (code == null || code.isEmpty()) {
            msg = "code为null,授权信息错误！";
            status = "error";
            BaseResponseBody baseResponseBody = new BaseResponseBody();
            baseResponseBody.setMessage(msg);
            baseResponseBody.setStatus(status);
            request.setAttribute("info", baseResponseBody);
            request.getRequestDispatcher(url).forward(request, response);
        }

        Authorize auth = new Authorize(AppContext.APPID, AppContext.APPSECRET);
        String queryTokenRS = auth.querytoken(code, AppContext.BACKURL);


        if (queryTokenRS == null || queryTokenRS.isEmpty()) {
            msg = "queryTokenRS为null,授权信息错误！";
            status = "error";
            BaseResponseBody baseResponseBody = new BaseResponseBody();
            baseResponseBody.setMessage(msg);
            baseResponseBody.setStatus(status);
            request.setAttribute("info", baseResponseBody);
            request.getRequestDispatcher(url).forward(request, response);
        }
        JSONObject json = JSONObject.fromObject(queryTokenRS);

        if (json == null) {
            msg = "json为null,json转换错误！";
            status = "error";
            BaseResponseBody baseResponseBody = new BaseResponseBody();
            baseResponseBody.setMessage(msg);
            baseResponseBody.setStatus(status);
            request.setAttribute("info", baseResponseBody);
            request.getRequestDispatcher(url).forward(request, response);
        }

        if (Objects.requireNonNull(json).has("access_token")) {

            String accessToken = json.getString("access_token");
            redisUtil.set("access_token", accessToken);
            //授权查询
            String text1 = auth.getManInstance(accessToken).query();
            System.out.println(text1);

            User user = new User(accessToken);
            YbUser ybUser = new YbUser();

            JSONObject userInfo = JSONObject.fromObject(user.me()).getJSONObject("info");
//            JSONObject otherUserInfo = JSONObject.fromObject(user.other(2));

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
            tokenResponseBody.setToken(accessToken);
            tokenResponseBody.setUser(ybUser);
            tokenResponseBody.setInfo(products);
            tokenResponseBody.setMessage("成功获取用户信信息、授权信息及产品信息");
            tokenResponseBody.setStatus("success");
            request.setAttribute("info", tokenResponseBody);
            request.getRequestDispatcher(url).forward(request, response);
        } else {
            msg = "获取token信息错误！";
            status = "error";
            BaseResponseBody baseResponseBody = new BaseResponseBody();
            baseResponseBody.setMessage(msg);
            baseResponseBody.setStatus(status);
            request.setAttribute("info", baseResponseBody);
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}
