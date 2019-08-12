package com.ein.Service;

import com.ein.Model.BBS;
import com.ein.Model.User_BBS;
import com.ein.Service.BaseService.BaseService;
import com.ein.Service.BaseService.NavigationPageService;
import com.ein.Utils.Result;


public interface BBSService extends BaseService<BBS>,NavigationPageService<BBS> {
    public Result addReply(User_BBS user_bbs);
    public Result searchReplyByBBSId(int bbsId);
}
