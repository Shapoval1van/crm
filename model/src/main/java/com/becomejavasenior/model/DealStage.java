package com.becomejavasenior.model;

import java.io.Serializable;

public enum DealStage implements Serializable {
    PRIMARY_CONTACT,
    NEGOTIATION,
    DECIDING,
    AGREEMENT,
    SUCCESS,
    FAILED_AND_CLOSED;

    public static DealStage getDealStageById(int id){
        for(DealStage dealStage : DealStage.values()){
            if(dealStage.ordinal() == id){
                return dealStage;
            }
        }
        return null;
    }
}
