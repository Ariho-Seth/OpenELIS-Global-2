/**
 * The contents of this file are subject to the Mozilla Public License Version 1.1 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the
 * License at http://www.mozilla.org/MPL/
 *
 * <p>Software distributed under the License is distributed on an "AS IS" basis, WITHOUT WARRANTY OF
 * ANY KIND, either express or implied. See the License for the specific language governing rights
 * and limitations under the License.
 *
 * <p>The Original Code is OpenELIS code.
 *
 * <p>Copyright (C) The Minnesota Department of Health. All Rights Reserved.
 */
package org.openelisglobal.audittrail.valueholder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.openelisglobal.common.valueholder.BaseObject;

/**
 * @author Hung Nguyen
 * @date created 09/12/2006
 */

@Getter
@Setter
@Entity
@Table(name = "history")
public class History extends BaseObject<String> {

    @Id
    @GeneratedValue(generator = "string-sequence-generator")
    @GenericGenerator(name = "string-sequence-generator", strategy = "org.openelisglobal.hibernate.resources.StringSequenceGenerator", parameters = {
            @Parameter(name = "sequence_name", value = "history_seq") })
    @Type(type = "org.openelisglobal.hibernate.resources.usertype.LIMSStringNumberUserType")
    @Column(name = "ID", precision = 10, scale = 0, nullable = false)
    private String id;

    @Type(type = "org.openelisglobal.hibernate.resources.usertype.LIMSStringNumberUserType")
    @Column(name = "reference_id", precision = 22, scale = 0)
    private String referenceId;

    @Type(type = "org.openelisglobal.hibernate.resources.usertype.LIMSStringNumberUserType")
    @Column(name = "reference_table", precision = 22, scale = 0)
    private String referenceTable;

    @Column(name = "timestamp", precision = 7, nullable = false)
    private Timestamp timestamp;

    @Column(name = "activity", precision = 1, nullable = false)
    private String activity;

    @Column(name = "changes", nullable = true)
    private byte[] changes;

    @Type(type = "org.openelisglobal.hibernate.resources.usertype.LIMSStringNumberUserType")
    @Column(name = "sys_user_id", precision = 22, scale = 0)
    private String sys_user_id;

    @Override
    public String getSysUserId() {
        return sys_user_id;
    }

    @Override
    public void setSysUserId(String sys_user_id) {
        this.sys_user_id = sys_user_id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
