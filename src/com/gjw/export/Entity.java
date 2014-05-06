package com.gjw.export;

import java.io.Serializable;

/**
 * Created by Administrator on 14-5-6.
 */
public class Entity implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String name;
    private String text;
    private String type;

    public String getNeme()
    {
        return this.name; }

    public void setNeme(String neme) {
        this.name = neme; }

    public String getText() {
        return this.text; }

    public void setText(String text) {
        this.text = text; }

    public String getType() {
        return this.type; }

    public void setType(String type) {
        this.type = type;
    }
}
