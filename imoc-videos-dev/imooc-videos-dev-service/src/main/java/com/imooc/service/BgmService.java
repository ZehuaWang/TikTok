package com.imooc.service;

import com.imooc.pojo.Bgm;

import java.util.List;

public interface BgmService {

    /**
     * query the bgm list
     * @return
     */
    public List<Bgm> queryBgmList();

    /**
     * 根据bgmId查询BGM
     * @param bgmId
     * @return Bgm
     */
    public Bgm queryBgmById(String bgmId);
}
