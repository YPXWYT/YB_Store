//package com.tna.yb_store.config;
//
//
//import com.tna.yb_store.interceptor.MainInterceptor;
//import com.tna.yb_store.interceptor.Interceptor1;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//// 以下WebMvcConfigurerAdapter 比较常用的重写接口
//// /** 解决跨域问题 **/
//// public void addCorsMappings(CorsRegistry registry) ;
//// /** 添加拦截器 **/
//// void addInterceptors(InterceptorRegistry registry);
//// /** 这里配置视图解析器 **/
//// void configureViewResolvers(ViewResolverRegistry registry);
//// /** 配置内容裁决的一些选项 **/
//// void configureContentNegotiation(ContentNegotiationConfigurer
//// configurer);
//// /** 视图跳转控制器 **/
//// void addViewControllers(ViewControllerRegistry registry);
//// /** 静态资源处理 **/
//// void addResourceHandlers(ResourceHandlerRegistry registry);
//// /** 默认静态资源处理器 **/
//// void configureDefaultServletHandling(DefaultServletHandlerConfigurer
//// configurer);
//@Configuration
//public class InterceptorConfigure implements WebMvcConfigurer {
//
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//
//    }
//
//    @Bean
//    public HandlerInterceptor getMyMainInterceptor() {//        getMyInterceptor方法为拦截器实例注入方法。
//        return new MainInterceptor();
//
//    }
//
//    @Bean
//    public HandlerInterceptor getMyInterceptor1() {//        getMyInterceptor方法为拦截器实例注入方法。
//        return new Interceptor1();
//
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        registry.addInterceptor(getMyMainInterceptor()).addPathPatterns("/**");
//        registry.addInterceptor(getMyInterceptor1()).addPathPatterns("/**");
//        WebMvcConfigurer.super.addInterceptors(registry);
//    }
//}
