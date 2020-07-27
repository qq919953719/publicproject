package com.cuichuang.common.bean;

import java.io.Serializable;

/**
 * Created by ${CC} on 2017/12/1.
 */

public class LoginBean implements Serializable {


    /**
     * code : 0
     * msg : 登录成功
     * data : {"uid":"2","phone":"13900000001","nickname":"崔狗狗","headpic":"","score":"2","roleid":"0","status":"0","token":"Tf2iSvUfkCOoUnuO4/JJhK70NTktT+e12NVuKLXMkNo="}
     */






        /**
         * uid : 2
         * phone : 13900000001
         * nickname : 崔狗狗
         * headpic :
         * score : 2
         * roleid : 0
         * status : 0
         * token : Tf2iSvUfkCOoUnuO4/JJhK70NTktT+e12NVuKLXMkNo=
         */

        private String uid;
        private String phone;
        private String realname;
        private String headpic;
        private String score;
        private String status;
        private String token;
        private String oid;
        private String oname;
        private String type;   //1表示正式党员；2表示预备党员；3表示积极分子 4群众
        private String is_lh; //1表示是龙虎网；0表示不是龙虎网
        private String coname;

        public String getApplycheck() {
            return applycheck;
        }

        public void setApplycheck(String applycheck) {
            this.applycheck = applycheck;
        }

        private String applycheck;//党员发展权限 0没有权限 1有权限
        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        private String num;
        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        private String mobile;
        private int roleid; //0 普通党员  2支部书记  6总支书记  1组织部

        public int getRoleid() {
            return roleid;
        }

        public void setRoleid(int roleid) {
            this.roleid = roleid;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getConame() {
            return coname;
        }

        public void setConame(String coname) {
            this.coname = coname;
        }

        public String getParty_duty() {
            return party_duty;
        }

        public void setParty_duty(String party_duty) {
            this.party_duty = party_duty;
        }

        private String party_duty;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }


        public String getHeadpic() {
            return headpic;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public String getOname() {
            return oname;
        }

        public void setOname(String oname) {
            this.oname = oname;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIs_lh() {
            return is_lh;
        }

        public void setIs_lh(String is_lh) {
            this.is_lh = is_lh;
        }

}
