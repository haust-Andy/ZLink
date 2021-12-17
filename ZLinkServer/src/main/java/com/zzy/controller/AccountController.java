package com.zzy.controller;

import com.zzy.pojo.Bean.ResultInfo;
import com.zzy.pojo.entity.User;
import com.zzy.service.AccountService;
import com.zzy.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/ZLink")
public class AccountController {

    @Resource
    AccountService accountService;

    @Autowired
    TokenUtil tokenUtil;

    @RequestMapping("/test")
    public String test() {
        return "s";
    }

    @RequestMapping("/verifyLogin")
    public ResultInfo verifyLogin(@RequestBody User user) {
        String base64String=null;
        User selectUser = accountService.verifyLogin(user);
        if (selectUser != null) {
            selectUser.setAccountToken(tokenUtil.getToken());
            accountService.setAccountToken(selectUser);

            return new ResultInfo(200, selectUser);
        }
        return new ResultInfo(401, null);
    }

    @RequestMapping("/logout")
    public ResultInfo logout() {
        return new ResultInfo(200, accountService.delAccountToken());
    }

    @RequestMapping("/registerAccount")
    public ResultInfo registerAccount(@RequestBody User user) {
        if (accountService.userIsExist(user))//已存在相同账号
            return new ResultInfo(401, "已存在相同账号");
        if (!accountService.createAccount(user))//未知原因未创建成功
            return new ResultInfo(402, "未知原因未创建成功");
        return new ResultInfo(200, true);
    }


    @RequestMapping("/updateAccount")
    public ResultInfo updateAccount(HttpSession session, @RequestBody User getsUser) {
        User user = (User) session.getAttribute("user");
        user.setAccountName(getsUser.getAccountName());
        user.setAccountEmail(getsUser.getAccountEmail());
        Boolean flag = accountService.updateAccountById(user);
        if (flag)
            return new ResultInfo(200, true);
        else
            return new ResultInfo(405, false);
    }

    @RequestMapping("/tokenError")
    public ResultInfo tokenError() {
        return new ResultInfo(401, "请先登录");
    }


    @RequestMapping("/searchUser")
    public ResultInfo searchUser(@RequestBody User user) {
        User user1=new User();
        if (user.getAccountName() != null) {
            user1 = accountService.selectUserByName(user.getAccountName());
        } else if (user.getAccountNumber() != null) {
            user1 = accountService.selectUserByNumber(user.getAccountNumber());
        }
        return new ResultInfo(200,user1);
    }


}
