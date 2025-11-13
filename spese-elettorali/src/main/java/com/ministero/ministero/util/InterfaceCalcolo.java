package com.ministero.ministero.util;

import java.util.List;

import com.ministero.ministero.model.Elezione;

public interface InterfaceCalcolo {
    
    public List<Integer> LeggiAnni();

    public List<Elezione> LeggiElezioni(int anno);

    
}

