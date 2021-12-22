package com.example.llz.cloud2.form.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import lombok.Data;

/**
 * @Description  
 * @Author  lilingzhi
 * @Date 2021-12-14 
 */

@Data
@Entity
@Table ( name ="formdomaininfo" )
public class Formdomaininfo  implements Serializable {

	private static final long serialVersionUID =  6395497207405791192L;
	@Id
   	@Column(name = "RID" )
	private String rid;

   	@Column(name = "BGCOLOR" )
	private String bgcolor;

   	@Column(name = "EXCHANGEID" )
	private String exchangeid;

   	@Column(name = "FALIGN" )
	private String falign;

   	@Column(name = "FCALCVALUE" )
	private String fcalcvalue;

   	@Column(name = "FDEFAULTVALUE" )
	private String fdefaultvalue;

   	@Column(name = "FDIC" )
	private String fdic;

   	@Column(name = "FEXTENDPROPERTY" )
	private String fextendproperty;

   	@Column(name = "FFORMATE" )
	private String fformate;

   	@Column(name = "FHEIGHT" )
	private String fheight;

   	@Column(name = "FLENGTH" )
	private String flength;

   	@Column(name = "FNAME" )
	private String fname;

   	@Column(name = "FONTS" )
	private String fonts;

   	@Column(name = "FPRINTDEFAULTVALUE" )
	private String fprintdefaultvalue;

   	@Column(name = "FSECURITYTYPE" )
	private String fsecuritytype;

   	@Column(name = "FTEMPLATE" )
	private String ftemplate;

   	@Column(name = "FTITLE" )
	private String ftitle;

   	@Column(name = "FTYPE" )
	private String ftype;

   	@Column(name = "FVALIDATEFN" )
	private String fvalidatefn;

   	@Column(name = "FVALIGN" )
	private String fvalign;

   	@Column(name = "FVALUETYPE" )
	private String fvaluetype;

   	@Column(name = "FWIDTH" )
	private String fwidth;

   	@Column(name = "INCONTAINER" )
	private String incontainer;

   	@Column(name = "MARGIN" )
	private String margin;

   	@Column(name = "PRID" )
	private String prid;

   	@Column(name = "RELEVANCEFIELD" )
	private String relevancefield;

   	@Column(name = "FZINDEX" )
	private String fzindex;

}
