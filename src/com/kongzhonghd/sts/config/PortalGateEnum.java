package com.kongzhonghd.sts.config;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/21/13
 * Time: 1:39 PM
 * Portal Gate类型
 */
public enum PortalGateEnum
{
    CliGate(400),
    GameGate(401),
    WebGate(402);
    private int gateType;

    PortalGateEnum(int gateType)
    {
        this.gateType = gateType;
    }

    public int getGateType()
    {
        return this.gateType;
    }
}
