package com.gjw.export.test;

import java.io.Serializable;

/**
 * Created by Administrator on 14-5-6.
 */
public class Medicine implements Serializable {

    private int id;
    /**
     * 药品名称
     * @hibernate.property
     * unique="true"
     */
    private String name;
    /**
     * 放药药框
     * @hibernate.property
     */
    private String ark;
    /**
     * 条形码
     * @hibernate.property
     *
     */
    private String barCode;
    /**
     * 功效描述
     * @hibernate.property
     */
    private String efficacy;
    /**
     * 进货价格
     * @hibernate.property
     */
    private double buyPrice;
    /**
     * 出售价格
     * @hibernate.property
     */
    private double sellPrice;
    /**
     * 库存量
     * @hibernate.property
     */
    private double stockpile;
    /**
     * 库存不足提示数量
     * @hibernate.property
     */
    private double cueNumber;
    /**
     * 打折百分比
     * @hibernate.property
     */
    private double rebate;
    /**
     * 提成百分比
     * @hibernate.property
     */
    private double rakeOff;
    /**
     * 拼音简查码
     * @hibernate.property
     */
    private String spellShort;
    /**
     * 数字简查码
     * @hibernate.property
     */
    private String numberShort;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getArk() {
        return ark;
    }
    public void setArk(String ark) {
        this.ark = ark;
    }
    public String getBarCode() {
        return barCode;
    }
    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
    public String getEfficacy() {
        return efficacy;
    }
    public void setEfficacy(String efficacy) {
        this.efficacy = efficacy;
    }
    public double getBuyPrice() {
        return buyPrice;
    }
    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }
    public double getSellPrice() {
        return sellPrice;
    }
    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }
    public double getStockpile() {
        return stockpile;
    }
    public void setStockpile(double stockpile) {
        this.stockpile = stockpile;
    }
    public double getCueNumber() {
        return cueNumber;
    }
    public void setCueNumber(double cueNumber) {
        this.cueNumber = cueNumber;
    }
    public double getRebate() {
        return rebate;
    }
    public void setRebate(double rebate) {
        this.rebate = rebate;
    }
    public double getRakeOff() {
        return rakeOff;
    }
    public void setRakeOff(double rakeOff) {
        this.rakeOff = rakeOff;
    }
    public String getSpellShort() {
        return spellShort;
    }
    public void setSpellShort(String spellShort) {
        this.spellShort = spellShort;
    }
    public String getNumberShort() {
        return numberShort;
    }
    public void setNumberShort(String numberShort) {
        this.numberShort = numberShort;
    }

    public Medicine() {
        // TODO Auto-generated constructor stub
    }
    public Medicine(String name, String ark, String barCode,
                    double buyPrice,  double stockpile,String efficacy,
                    double rebate, double sellPrice) {
        this.name = name;
        this.ark = ark;
        this.barCode = barCode;
        this.buyPrice = buyPrice;
        this.stockpile = stockpile;
        this.rebate = rebate;
        this.sellPrice = sellPrice;
        this.efficacy = efficacy;

    }



}
