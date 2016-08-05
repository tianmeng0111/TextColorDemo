package com.tm.example.textcolordemo;

import java.util.List;

/**
 * Created by Tian on 2016/8/2.
 */
public class jsonTest {

    /**
     * agent_id : 1000000020235697
     * agent_name : 崔凯灿
     * agent_org : 新房运营-直销中心-开发区-二区-四组
     * agent_tel : 15632172591
     */

    private List<AgentsBean> agents;

    public List<AgentsBean> getAgents() {
        return agents;
    }

    public void setAgents(List<AgentsBean> agents) {
        this.agents = agents;
    }

    public static class AgentsBean {
        private long agent_id;
        private String agent_name;
        private String agent_org;
        private String agent_tel;

        public long getAgent_id() {
            return agent_id;
        }

        public void setAgent_id(long agent_id) {
            this.agent_id = agent_id;
        }

        public String getAgent_name() {
            return agent_name;
        }

        public void setAgent_name(String agent_name) {
            this.agent_name = agent_name;
        }

        public String getAgent_org() {
            return agent_org;
        }

        public void setAgent_org(String agent_org) {
            this.agent_org = agent_org;
        }

        public String getAgent_tel() {
            return agent_tel;
        }

        public void setAgent_tel(String agent_tel) {
            this.agent_tel = agent_tel;
        }
    }
}
