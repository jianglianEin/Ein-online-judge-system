package com.ein.Service;

import com.ein.Model.BBS;
import com.ein.Model.User_BBS;
import com.ein.Utils.Result;


public interface BBSService extends BaseService<BBS>{

    public Result searchBBSByGet(String bbsId);
    public Result addByPost(BBS bbs);
    public Result searchBBSByPage(int page,int bbsNum);
    public Result RemoveBBSById(int id);
    public Result get_resent_notic(int searchNum);
    public Result searchBBSCount();
    public Result addReplyByPost(User_BBS user_bbs);
    public Result searchReplyByBBSId(int bbsId);
}
