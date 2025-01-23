package com.project.be_java_hisp_w29_g10.service;

public interface IFollowService {
    //Metodo para devolver la cantidad de seguidores de un vendedor(US2)
    Integer getCountFollowers(Long sellerId);
}
