package com.anjbo.chromejs.entity;

import java.util.ArrayList;
import java.util.List;

public class LoginConfig {
    private List<LoginSeq> mLoginSequences = new ArrayList<LoginSeq>();

    public LoginConfig(List<LoginSeq> loginSeqs) {
        mLoginSequences = loginSeqs;
    }

    public List<LoginSeq> getLoginSequences() {
        return mLoginSequences;
    }
}
