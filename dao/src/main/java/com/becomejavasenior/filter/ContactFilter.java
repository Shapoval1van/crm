package com.becomejavasenior.filter;

import com.becomejavasenior.model.DealStage;
import com.becomejavasenior.model.Tag;
import com.becomejavasenior.model.Task;
import com.becomejavasenior.model.User;

import java.util.Set;

public class ContactFilter {
    ActionType actionType;

    ActionTypeTimePeriod actionTypeTimePeriod;

    Set<DealStage> stages;

    User responsible;

    Task task;

    Tag tag;

}
