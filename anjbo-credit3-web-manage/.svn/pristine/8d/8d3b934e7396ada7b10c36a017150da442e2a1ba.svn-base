angular.module("anjboApp", ['bsTable']).controller("agencyListCtrl", function($scope, $http, $filter, $state, box, route,parent) {


	//获取渠道经理
	$http({
		method: 'post',
		url: "/credit/user/user/v/searchByType",
		data:{type:"role",name:"渠道经理",agencyId:"1"}
	}).success(function(data) {
		$scope.channelManagerList = data.data;
	});
	
	//初始化数据
	$http({
		method: 'post',
		url:'/credit/data/dict/v/search',
		data:{type:"cityList"}
	}).success(function(data) {
		if("SUCCESS"==data.code){
			$scope.cityList = data.data;
		}
	});
	//初始化数据
	$http({
		method: 'post',
		url:'/credit/data/dict/v/search',
		data:{type:"agencyType"}
	}).success(function(data) {
		if("SUCCESS"==data.code){
			$scope.agencyType = data.data;
		}
	});
	//初始化数据
	$http({
		method: 'post',
		url:'/credit/data/dict/v/search',
		data:{type:"cooperativeMode"}
	}).success(function(data) {
		if("SUCCESS"==data.code){
			$scope.cooperativeMode = data.data;
		}
	});
	
	window.operateEvents = {
		'click .maintain': function(e, value, row, index) {
			$state.go("agencyMaintainEdit", { 'agencyId':row.id,'productCode':'100',"orderNo":row.orderNo});
		},
		'click .detail': function(e, value, row, index) {
			$state.go("agencyMaintainDetail", { 'agencyId':row.id,'productCode':'100',"orderNo":row.orderNo});
		}
	};

	$scope.page = new Object();
	
	function getParams(data) {
		$scope.page.start = data.offset;
		$scope.page.pageSize = data.limit;
		return $scope.page;
	}

	var url = "/credit/user/agency/v/page";
	$scope.query = function() {
		if($scope.page.startSignDate
			&&$scope.page.endSignDate
			&&$scope.page.startSignDate>$scope.page.endSignDate){
			box.boxAlert("结束日期不能小于开始日期");
			return;
		}
		$("#table").bootstrapTable('refresh', { url: url,pageNumber:1 });
	}
	$scope.restart = function(){
		$scope.page.name = "";
		$scope.page.startSignDate = "";
		$scope.page.endSignDate = "";
		$scope.page.openCity = "";
		$scope.page.statusExtension = "-1";
		$scope.page.agencyType = "";
		$scope.page.cooperativeModeId = "";
		$scope.page.cooperativeModeId = "";
		$scope.page.signStatus = "-1";
		$scope.page.chanlMan = "";
	}
	$scope.agencyList = {
		options: {
			method: "post",
			url: url,
			queryParams: getParams,
			sidePagination: 'server',
			undefinedText: "-",
			cache: false,
			striped: true,
			pagination: true,
			pageNumber: 1,
			pageSize: 15,
			pageList: ['All'],
			columns: [{
				title: '机构名称',
				field: 'name',
				align: 'center',
				valign: 'bottom',
				events: operateEvents,
				formatter: function(value, row, index) {

					if(""==value){
						value = "-";
					} else if(value&&value.length>20){
						var len = value.length;
						var k = len%20;
						var i = (len - k)/20;
						if(k>0){
							i += 1;
						}
						var tmp = "";
						for (var j=1;j<=i;j++){
							if(""==tmp){
								tmp = value.substring(0,20*j);
							} else if((20*j)>value.length){
								tmp += "<br/>"+value.substring(20*(j-1),value.length);
							} else {
								tmp += "<br/>"+value.substring(20*(j-1),20*j);
							}

						}
						value = tmp;
					}
					return value;
				}
			}, {
				title: '机构码',
				field: 'agencyCode',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '机构状态',
				field: 'signStatus',
				align: 'center',
				valign: 'bottom',
				events: operateEvents,
				formatter: function(value, row, index) {
					var val = "";
					if(1==value){
						val = "未签约";
					} else if(2==value){
						val = "已签约";
					} else if(3==value){
						val = "已解约";
					}
					return val;
				}
			}, {
				title: '账号状态',
				field: 'status',
				align: 'center',
				valign: 'bottom',
				events: operateEvents,
				formatter: function(value, row, index) {
					var val = "";
					if(0==value){
						val = "冻结";
					} else if(1==value){
						val = "解冻";
					}
					return val;
				}
			}, {
				title: '剩余额度（万元）',
				field: 'surplusQuota',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '机构类型',
				field: 'typeName',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '签约日期',
				field: 'signDate',
				align: 'center',
				valign: 'bottom',
				formatter: function(value, row, index) {
					if(value){
						return $filter('date')(value, 'yyyy-MM-dd HH:mm:ss');
					}
					return value;
				}
			},{
				title: '合作模式',
				field: 'cooperativeMode',
				align: 'center',
				valign: 'bottom'
			},{
				title: '开通城市',
				field: 'openCity',
				align: 'center',
				valign: 'bottom'
			},{
				title: '渠道经理',
				field: 'chanManName',
				align: 'center',
				valign: 'bottom'
			},{
				title: '联系人手机号',
				field: 'contactTel',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '操作',
				field: 'operate',
				align: 'center',
				valign: 'bottom',
				events: operateEvents,
				formatter: function(value, row, index) {
					var html = "";
					if(2==row.signStatus&&(parent.userDto.uid==row.expandManagerUid||parent.userDto.uid==row.chanlMan||parent.userDto.uid=='123456')){
						html = '<a class="maintain" href="javascript:void(0)" >维护</a>&nbsp;&nbsp;'
					}
					html += '<a class="detail" href="javascript:void(0)" >查看</a>&nbsp;&nbsp;';
					return html;
				}
			}]
		}
	};

});