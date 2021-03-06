package cn.lshang.web.servlet;

import cn.lshang.domain.resultInfo;
import cn.lshang.domain.user;
import cn.lshang.service.impl.userServiceImpl;
import cn.lshang.service.userService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/settingServlet")
public class settingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //response.setContentType("text/html;charset=UTF-8");

        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();

        //2.封装对象
        user user = new user();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //3.调用service完成注册
        userService service = new userServiceImpl();
        boolean flag = service.changeUser(user);

        resultInfo info = new resultInfo();
        //4.响应结果
        if(flag){
            //注册成功
            info.setFlag(true);
            info.setErrorMsg("修改成功!");
        }else{
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("修改失败！");
        }

        //将info对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);

        //将json数据写回客户端
        //设置content-type
        //response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
