package com.example.petstorebackend.AccountLogAndRegister.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.petstorebackend.AccountLogAndRegister.common.CommonResponse;
import com.example.petstorebackend.AccountLogAndRegister.entity.Account;
import com.example.petstorebackend.AccountLogAndRegister.entity.BannerData;
import com.example.petstorebackend.AccountLogAndRegister.entity.Profile;
import com.example.petstorebackend.AccountLogAndRegister.entity.SignOn;
import com.example.petstorebackend.AccountLogAndRegister.persistence.AccountMapper;
import com.example.petstorebackend.AccountLogAndRegister.persistence.BannerDataMapper;
import com.example.petstorebackend.AccountLogAndRegister.persistence.ProfileMapper;
import com.example.petstorebackend.AccountLogAndRegister.persistence.SignOnMapper;
import com.example.petstorebackend.AccountLogAndRegister.service.AccountService;
import com.example.petstorebackend.AccountLogAndRegister.VO.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private BannerDataMapper bannerDataMapper;
    @Autowired
    private SignOnMapper signOnMapper;
    @Autowired
    private ProfileMapper profileMapper;
    @Override
    public CommonResponse<AccountVO> getAccount(String username, String password) {
        QueryWrapper<SignOn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        queryWrapper.eq("password",password);
        SignOn signOn = signOnMapper.selectOne(queryWrapper);
        if(signOn == null){
            return CommonResponse.createForError("password or username error");
        }
        return getAccount(username);
    }

    /**
     * 内部调用使用username快速获取accountVo
     * @Author Zhao
     * @param username
     * @return Account类型的返回Response
     */
    @Override
    public CommonResponse<AccountVO> getAccount(String username) {
        Account account = accountMapper.selectById(username);
        if(account == null){
            return CommonResponse.createForError("无法找到账户信息");
        }
        Profile profile = profileMapper.selectById(username);
        if(profile == null){
            return CommonResponse.createForError("不存在用户名为"+username+"的登录活动信息");
        }
        BannerData bannerData = bannerDataMapper.selectById(profile.getFavcategory());

        return CommonResponse.createForSuccess(entityToVO(account,profile,bannerData));
    }

    @Override
    public CommonResponse<SignOn> addAccount(String username, String password) {
        if (getAccount(username).getStatus()!=2000) {
            Account account = new Account();
            account.setUsername(username);
            Profile profile = new Profile();
            profile.setUserid(username);
            SignOn signOn = new SignOn();
            signOn.setUsername(username);
            signOn.setPassword(password);
            accountMapper.insert(account);
            profileMapper.insert(profile);
            signOnMapper.insert(signOn);
            return CommonResponse.createForSuccess(2000,
                    "注册成功",
                   signOn);
        }
        else {
            return CommonResponse.createForError(1001,
                    "Invalid registration parameters",
                    null);
        }
    }


    @Override
    public CommonResponse<AccountVO> updateAccount(AccountVO accountVO) {
        System.out.println(accountVO);
        Account account = new Account();
        account.setUsername(accountVO.getUsername());
        account.setEmail(accountVO.getEmail());
        account.setFirstName(accountVO.getFirstName());
        account.setLastName(accountVO.getLastName());
        account.setStatus(accountVO.getStatus());
        account.setAddress1(accountVO.getAddress1());
        account.setAddress2(accountVO.getAddress2());
        account.setCity(accountVO.getCity());
        account.setCountry(accountVO.getCountry());
        account.setState(accountVO.getState());
        account.setZip(accountVO.getZip());
        account.setPhone(accountVO.getPhone());
        System.out.println(account);
        accountMapper.updateById(account);

        Profile profile = new Profile();
        profile.setUserid(accountVO.getUsername());
        profile.setFavcategory(accountVO.getFavouriteCategory());
        profile.setBanneropt(accountVO.isBannerOption() ? 1 : 0);
        profile.setMylistopt(accountVO.isListOption() ? 1 : 0);
        profile.setLangpref(accountVO.getLanguagePreference());
        profileMapper.updateById(profile);
        return getAccount(accountVO.getUsername());
    }

    private AccountVO entityToVO(Account account, Profile profile, BannerData bannerData){
        AccountVO accountVO = new AccountVO();

        //account table
        accountVO.setUsername(account.getUsername());
        accountVO.setPassword("");
        accountVO.setEmail(account.getEmail());
        accountVO.setFirstName(account.getFirstName());
        accountVO.setLastName(account.getLastName());
        accountVO.setStatus(account.getStatus());
        accountVO.setAddress1(account.getAddress1());
        accountVO.setAddress2(account.getAddress2());
        accountVO.setCity(account.getCity());
        accountVO.setCountry(account.getCountry());
        accountVO.setState(account.getState());
        accountVO.setZip(account.getZip());
        accountVO.setPhone(account.getPhone());

        //profile table
        accountVO.setLanguagePreference(profile.getLangpref());
        accountVO.setBannerOption(profile.getBanneropt() == 1);
        accountVO.setListOption(profile.getMylistopt() == 1);

        //banner table
        if(profile.getBanneropt() == 1){
            accountVO.setFavouriteCategory(profile.getFavcategory());
            accountVO.setBannerName(bannerData.getBannerName());
        }else{
            accountVO.setFavouriteCategory("");
            accountVO.setBannerName("");
        }
        return accountVO;
    }

    @Override
    public CommonResponse<AccountVO> updateAccount(String password, String username) {
        SignOn signOn = new SignOn();
        signOn.setUsername(username);
        signOn.setPassword(password);
        signOnMapper.updateById(signOn);
        return getAccount(username);
    }
}