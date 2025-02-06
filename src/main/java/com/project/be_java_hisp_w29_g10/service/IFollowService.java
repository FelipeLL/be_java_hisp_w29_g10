package com.project.be_java_hisp_w29_g10.service;

import java.util.List;

public interface IFollowService {
    //Metodo para devolver la lista de vendedores que sigue de un usuario(US4)
    List<Long> getSellersFollowedByUser(Long id);
}
