package com.example.petstorebackend.AccountLogAndRegister.service;

import com.example.petstorebackend.AccountLogAndRegister.common.CommonResponse;
import com.example.petstorebackend.AccountLogAndRegister.VO.AccountVO;
import com.example.petstorebackend.AccountLogAndRegister.entity.SignOn;

public interface AccountService {
    CommonResponse<AccountVO> getAccount(String username, String password);
    CommonResponse<AccountVO> getAccount(String username);
    CommonResponse<SignOn> addAccount(String username, String password);
    CommonResponse<AccountVO> updateAccount(AccountVO accountVO);
    CommonResponse<AccountVO> updateAccount(String username, String password);
}