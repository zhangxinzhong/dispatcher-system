CREATE TABLE TB_PROJECT_BASE(
	pb_code varchar(50) NOT NULL COMMENT '项目编号',
	pb_name varchar(200) NOT NULL COMMENT '项目名称',
	pb_region varchar(500) NOT NULL COMMENT '项目区域',
	implement_term varchar(5) NOT NULL COMMENT '实施期限',
	implement_cycle varchar(5) NOT NULL COMMENT '实施周期',
	acceptance_time varchar(20) NOT NULL COMMENT '验收时间',
	report_unit varchar(250) NOT NULL COMMENT '填报单位',
	report_people varchar(50) NOT NULL COMMENT '填报人',
	telephone varchar(11) NOT NULL COMMENT '联系电话',
	report_date varchar(50) NOT NULL COMMENT '填报日期',
	status varchar(5) NOT NULL COMMENT '状态',
	create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (pb_code) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT '项目基本信息';

CREATE TABLE TB_PROJECT_TOTAL_TARGET(
	pt_code varchar(50) NOT NULL COMMENT '指标编号',
	pb_code varchar(200) NOT NULL COMMENT '项目编号',
	one_level varchar(100) NOT NULL COMMENT '一级指标',
	two_level varchar(100) NOT NULL COMMENT '二级指标',
	three_level varchar(100) NOT NULL COMMENT '三级指标',
	pt_unit varchar(250) NOT NULL COMMENT '指标单位',
	pt_nature varchar(5) NOT NULL COMMENT '指标性质',
	total_target varchar(50) NOT NULL COMMENT '总目标',
	year_target_key varchar(500) NOT NULL COMMENT '年度指标年份',
	year_target_value varchar(500) NOT NULL COMMENT '年度指标年份值',
	create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (pt_code) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT '项目总体绩效目标表';

CREATE TABLE TB_PROJECT_SUB_BASE(
	psb_code varchar(50) NOT NULL COMMENT '子项目编号',
	pb_code varchar(200) NOT NULL COMMENT '项目编号',
	psb_name varchar(200) NOT NULL COMMENT '子项目名称',
	psb_region varchar(500) NOT NULL COMMENT '子项目区域',
	create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (pb_code) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT '项目基本信息';

CREATE TABLE TB_PROJECT_TOTAL_PLAN(
	ptp_code varchar(50) NOT NULL COMMENT '计划编号',
	psb_code varchar(50) NOT NULL COMMENT '子项目编号',
	implement_term varchar(5) NOT NULL COMMENT '实施期限',
	implement_cycle varchar(5) NOT NULL COMMENT '实施周期',
	report_unit varchar(250) NOT NULL COMMENT '填报单位',
	report_people varchar(50) NOT NULL COMMENT '填报人',
	telephone varchar(11) NOT NULL COMMENT '联系电话',
	report_date varchar(50) NOT NULL COMMENT '填报日期',
	initiate_unit_name varchar(250) NOT NULL COMMENT '牵头单位名称',
	initiate_unit_people varchar(50) NOT NULL COMMENT '牵头单位联系人',
	initiate_unit_tel varchar(11) NOT NULL COMMENT '牵头单位电话',
	responsibility_unit_name varchar(250) NOT NULL COMMENT '责任单位名称',
	responsibility_unit_leader varchar(50) NOT NULL COMMENT '责任单位领导',
	responsibility_unit_tel varchar(50) NOT NULL COMMENT '责任单位电话',
	create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (ptp_code) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT '项目总计划表';

CREATE TABLE TB_PROJECT_SUB_TOTAL_TARGET(
	ptpt_code varchar(50) NOT NULL COMMENT '指标编号',
	ptp_code varchar(200) NOT NULL COMMENT '计划编号',
	one_level varchar(100) NOT NULL COMMENT '一级指标',
	two_level varchar(100) NOT NULL COMMENT '二级指标',
	three_level varchar(100) NOT NULL COMMENT '三级指标',
	pt_unit varchar(250) NOT NULL COMMENT '指标单位',
	pt_nature varchar(5) NOT NULL COMMENT '指标性质',
	total_target varchar(50) NOT NULL COMMENT '总目标',
	year_target_key varchar(500) NOT NULL COMMENT '年度指标年份',
	year_target_value varchar(500) NOT NULL COMMENT '年度指标年份值',
	create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (ptpt_code) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT '项目总计划子项目目标';

CREATE TABLE TB_PROJECT_TOTAL_PLAN_FUND(
	ptpf_code varchar(50) NOT NULL COMMENT '资金编号',
	ptp_code varchar(200) NOT NULL COMMENT '计划编号',
	ptp_year varchar(50) NOT NULL COMMENT '资金年份',
	center_investment varchar(50) NOT NULL COMMENT '中央投资',
	center_execute varchar(50) NOT NULL COMMENT '中央执行',
	provincial_investment varchar(50) NOT NULL COMMENT '省级投资',
	provincial_execute varchar(50) NOT NULL COMMENT '省级执行',
	city_investment varchar(50) NOT NULL COMMENT '市县投资',
	city_execute varchar(50) NOT NULL COMMENT '市县执行',
	create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (pt_code) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT '项目总计划资金表';

CREATE TABLE TB_PROJECT_YEAR_PLAN(
	pyp_code varchar(50) NOT NULL COMMENT '计划编号',
	psb_code varchar(50) NOT NULL COMMENT '子项目编号',
	implement_term varchar(5) NOT NULL COMMENT '实施期限',
	report_unit varchar(250) NOT NULL COMMENT '填报单位',
	report_people varchar(50) NOT NULL COMMENT '填报人',
	telephone varchar(11) NOT NULL COMMENT '联系电话',
	report_date varchar(50) NOT NULL COMMENT '填报日期',
	status varchar(50) NOT NULL COMMENT '年计划状态',
	create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (pyp_code) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT '项目年计划表';

CREATE TABLE TB_PROJECT_YEAR_PLAN_MONTH(
	pym_code varchar(50) NOT NULL COMMENT '指标编号',
	pyp_code varchar(200) NOT NULL COMMENT '年计划编号',
	one_level varchar(100) NOT NULL COMMENT '一级指标',
	two_level varchar(100) NOT NULL COMMENT '二级指标',
	three_level varchar(100) NOT NULL COMMENT '三级指标',
	pt_unit varchar(250) NOT NULL COMMENT '指标单位',
	pt_nature varchar(5) NOT NULL COMMENT '指标性质',
	year_target varchar(50) NOT NULL COMMENT '本年度目标',
	year_accumulate_target varchar(50) NOT NULL COMMENT '年度累计目标',
	year_target_month_key varchar(500) NOT NULL COMMENT '年度指标月份',
	year_target_month_value varchar(500) NOT NULL COMMENT '年度指标月份值',
	create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (pym_code) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT '项目分解至月目标表';

CREATE TABLE TB_PROJECT_YEAR_PLAN_SCHEDULE(
	pysp_code varchar(50) NOT NULL COMMENT '进度计划编号',
	pyp_code varchar(200) NOT NULL COMMENT '年计划编号',
	month_value varchar(10) NOT NULL COMMENT '月份',
	implementation_phase varchar(100) NOT NULL COMMENT '实施阶段',
	repair_area varchar(100) NOT NULL COMMENT '拟修复面积',
	completion_rate varchar(50) NOT NULL COMMENT '完工率',
	center_fund varchar(50) NOT NULL COMMENT '中央资金',
	provincial_fund varchar(50) NOT NULL COMMENT '省级资金',
	city_fund varchar(50) NOT NULL COMMENT '市县资金',
	create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (pysp_code) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT '项目年度实施进度计划表';

CREATE TABLE TB_PROJECT_MONTH_SPEED(
	pms_code varchar(50) NOT NULL COMMENT '月进度编号',
	psb_code varchar(50) NOT NULL COMMENT '子项目编号',
	report_unit varchar(250) NOT NULL COMMENT '填报单位',
	report_people varchar(50) NOT NULL COMMENT '填报人',
	telephone varchar(11) NOT NULL COMMENT '联系电话',
	report_date varchar(50) NOT NULL COMMENT '填报日期',
	implement_term varchar(5) NOT NULL COMMENT '实施期限',
	create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (pms_code) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT '项目月进度表';

CREATE TABLE TB_PROJECT_MONTH_COMPLETE_SPEED(
	pmcs_code varchar(50) NOT NULL COMMENT '实施进度编号',
	pms_code varchar(50) NOT NULL COMMENT '月进度编号',
	stage varchar(50) NOT NULL COMMENT '实施阶段计划',
	expected_repair varchar(50) NOT NULL COMMENT '拟修复面积',
	completion_rate varchar(50) NOT NULL COMMENT '完工率',
	month_rate varchar(50) NOT NULL COMMENT '当月实施阶段',
	completion_status varchar(50) NOT NULL COMMENT '完成状态',
	repairs_reckle varchar(50) NOT NULL COMMENT '修复图斑',
	filling varchar(50) NOT NULL COMMENT '已修复填报',
	pmcs_file varchar(250) COMMENT '实施文件',
	pmcs_desc varchar(5000) COMMENT '备注',
	create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (pmcs_code) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT '项目月进度子项目进度表';

CREATE TABLE TB_PROJECT_MONTH_TARGET(
	pmt_code varchar(50) NOT NULL COMMENT '本月指标编号',
	pmcs_code varchar(50) NOT NULL COMMENT '实施进度编号',
	is_lagging varchar(2) NOT NULL COMMENT '是否滞后',
	lagging_desc varchar(2000) COMMENT '滞后说明',
	rectification_measures varchar(2000) COMMENT '整改措施',
	create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (pmt_code) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT '项目本月完成情况表';

CREATE TABLE TB_PROJECT_MONTH_TARGET_DETAIL(
	pytd_code varchar(50) NOT NULL COMMENT '指标编号明细',
	pmt_code varchar(50) NOT NULL COMMENT '本月指标编号',
	one_level varchar(100) NOT NULL COMMENT '一级指标',
	two_level varchar(100) NOT NULL COMMENT '二级指标',
	three_level varchar(100) NOT NULL COMMENT '三级指标',
	pt_unit varchar(250) NOT NULL COMMENT '指标单位',
	pt_nature varchar(5) NOT NULL COMMENT '指标性质',
	month_name varchar(50) NOT NULL COMMENT '月份',
	cumulative_value varchar(50) NOT NULL COMMENT '累计值',
	completion_status varchar(2000) NOT NULL COMMENT '完成情况',
	create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (pytd_code) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT '项目本月完成情况明细表';

CREATE TABLE TB_PROJECT_MONTH_FUND(
	pmf_code varchar(50) NOT NULL COMMENT '本月资金编号',
	pmcs_code varchar(50) NOT NULL COMMENT '实施进度编号',
	is_place varchar(2) NOT NULL COMMENT '是否到位',
	place_desc varchar(2000) COMMENT '未到位说明',
	rectification_measures varchar(2000) COMMENT '整改措施',
	is_mate varchar(2) NOT NULL COMMENT '是否与计划匹配',
	mate_desc varchar(2000) NOT NULL COMMENT '计划匹配说明',
	create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (pmf_code) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT '项目本月资金情况表';

CREATE TABLE TB_PROJECT_MONTH_FUND_DETAIL(
	pmfd_code varchar(50) NOT NULL COMMENT '指标编号明细',
	pmf_code varchar(50) NOT NULL COMMENT '本月资金编号',
	fund_type varchar(50) NOT NULL COMMENT '资金类型',
	plan_fund varchar(50) NOT NULL COMMENT '计划资金',
	place_fund varchar(50) NOT NULL COMMENT '到位资金',
	not_place_fund varchar(50) NOT NULL COMMENT '未到位资金',
	fund_execute_desc varchar(2000) NOT NULL COMMENT '资金执行情况',
	create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (pmfd_code) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT '项目本月完成情况明细表';

CREATE TABLE TB_PROJECT_POLYGON(
	pp_code varchar(50) NOT NULL COMMENT '项目图斑编号',
	pmcs_code varchar(50) NOT NULL COMMENT '实施进度编号',
	p_code varchar(50) NOT NULL COMMENT '图斑编号',
	create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (pp_code) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT '项目图斑表';

CREATE TABLE TB_POLYGON(
	p_code varchar(50) NOT NULL COMMENT '图斑编号',
	geojson text NOT NULL COMMENT '图斑地形',
	create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (p_code) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT '项目图斑表';