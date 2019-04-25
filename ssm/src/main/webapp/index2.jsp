<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工列表</title>
<%
	request.setAttribute("APP_PATH", request.getContextPath());
%>
<script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.4.0.min.js"></script>
<link rel="stylesheet" href="${APP_PATH}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" type="text/css">
<script type="text/javascript" src="${APP_PATH}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script id="ilt" src="https://xiani.toubiec.cn/player/js/player.js" key="257656c14d7341809d9d37ae27334ead"></script>
</head>
<body style="background-position:center; background-repeat:no-repeat;background-size: cover;" background="${APP_PATH}/static/image/bg.jpg">
<!-- 员工更改的模态框 -->
<div class="modal fade" id="empUpdatemodel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">员工修改</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal">
		  <div class="form-group">
		    <label class="col-sm-2 control-label">empName</label>
		    <div class="col-sm-10">
		    	<p class="form-control-static" id="empName_update_static"></p>
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">email</label>
		    <div class="col-sm-10">
		      <input type="email" name="email" class="form-control" id="email_update_input" placeholder="请输入您的邮箱">
		      <span class="help-block"></span>
		    </div>
		  </div>
		   <div class="form-group">
		    <label class="col-sm-2 control-label">gender</label>
		    <div class="col-sm-10">
		        <label class="radio-inline">
				  <input type="radio" name="gender" id="gender1_update_input" value="M" checked> 男
				</label>
				<label class="radio-inline">
				  <input type="radio" name="gender" id="gender2_update_input" value="F"> 女
				</label>
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">deptName</label>
		    <div class="col-sm-10">
		        <select class="form-control" name="dId" id="dept_update_select">
				 
				</select>
		    </div>
		  </div>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" id="emp_update_btn">更新</button>
      </div>
    </div>
  </div>
</div>


<!-- 员工添加的模态框 -->
<div class="modal fade" id="empAddmodel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">员工添加</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal">
		  <div class="form-group">
		    <label class="col-sm-2 control-label">empName</label>
		    <div class="col-sm-10">
		      <input type="text" name="empName" class="form-control" id="empName_input" placeholder="请输入员工名称">
		      <span  class="help-block"></span>
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">email</label>
		    <div class="col-sm-10">
		      <input type="email" name="email" class="form-control" id="email_add_input" placeholder="请输入您的邮箱">
		      <span class="help-block"></span>
		    </div>
		  </div>
		   <div class="form-group">
		    <label class="col-sm-2 control-label">gender</label>
		    <div class="col-sm-10">
		        <label class="radio-inline">
				  <input type="radio" name="gender" id="gender1_add_input" value="M" checked> 男
				</label>
				<label class="radio-inline">
				  <input type="radio" name="gender" id="gender2_add_input" value="F"> 女
				</label>
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">deptName</label>
		    <div class="col-sm-10">
		        <select class="form-control" name="dId" id="dept_add_select">
				 
				</select>
		    </div>
		  </div>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" id="emp_save_btn">保存</button>
      </div>
    </div>
  </div>
</div>

	<!-- 搭建显示页面 -->
	<div class="container">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h1>吕世昊-SSM-CRUD</h1>
			</div>
		</div>
		<!-- 按钮 -->
		<div class="row">
			<div class="col-md-4 col-md-offset-8">
				<button class="btn btn-primary" id="emp_add_modal_btn">新增</button>
				<button class="btn btn-danger" id="emp_delete_all_btn">删除</button>
			</div>
		</div>
		<!-- 显示表格数据 -->
		<div class="row">
			<div class="col-md-12">
				<table class="table table-striped" id="emps_tables">
					<thead>
						<tr>
							<th><input type='checkbox' id="check_all" ></th>
							<th>#</th>
							<th>empName</th>
							<th>gender</th>
							<th>email</th>
							<th>deptName</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						
					</tbody>
				</table>
			</div>
		</div>
		<!-- 显示分页信息 -->
		<div class="row">
			<!-- 分页文字信息 -->
			<div class="col-md-6" id="page_info_area">
				
			</div>
			<!-- 分页条信息 -->
			<div class="col-md-6">
				<nav aria-label="Page navigation" id="tablenav">
				
				</nav>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		//定义一个全局的总记录数
		var totalRecord,currentNum;
		function to_page(pn){
			$("#check_all").prop("checked",false);
			$.ajax({
				url:"${APP_PATH}/emps",
				data:"pn="+pn,
				type:"get",
				success:function(result){
					//console.log(result);
					//1.解析并显示员工数据
					build_emps_table(result);
					//2.解析并显示分页信息
					build_page_info(result);
					//3.解析显示分页条数据
					build_page_nav(result);
				}
			})
		}
		//1.页面加载完成以后,直接去发送一个ajax请求,要到分页数据
		$(function(){
			to_page(1)
		})
		function build_page_nav(result){
			//清分页信息
			$("#tablenav").empty();
			var pageinfo=result.eMap.pageInfo;
			var ul=$("<ul></ul>").addClass("pagination");
			var firstPageLi=$("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
			var perPageLi=$("<li></li>").append($("<a></a>").append("&laquo;").attr("href","#"));
			if(pageinfo.hasPreviousPage==false){
				firstPageLi.addClass("disabled");
				perPageLi.addClass("disabled");
			}else{
				//首页点击
				perPageLi.click(function(){
					to_page(pageinfo.pageNum-1);
				});
				//上一页页点击
				firstPageLi.click(function(){
					to_page(1);
				});
			}
			var nextPageLi=$("<li></li>").append($("<a></a>").append("&raquo;").attr("href","#"));
			var lastPageLi=$("<li></li>").append($("<a></a>").append("末页").attr("href","#"));
			if(pageinfo.hasNextPage==false){
				nextPageLi.addClass("disabled");
				lastPageLi.addClass("disabled");
			}else{
				//下一页页点击
				nextPageLi.click(function(){
					to_page(pageinfo.pageNum+1);
				});
				//末页点击
				lastPageLi.click(function(){
					to_page(pageinfo.pages);
				});
			}
			//拼接ul标签
			ul.append(firstPageLi).append(perPageLi);
			$.each(pageinfo.navigatepageNums,function(index,item){
				var numLi=$("<li></li>").append($("<a></a>").append(item).attr("href","#"));
				if(pageinfo.pageNum==item){
					numLi.addClass("active");
				}
				//拼接点击事件
				numLi.click(function(){
					to_page(item);
				});
				ul.append(numLi);
			});
			ul.append(nextPageLi).append(lastPageLi).appendTo("#tablenav");
		}
		function build_page_info(result){
			//清空页码信息
			$("#page_info_area").empty();
			var pageinfo=result.eMap.pageInfo;
			$("#page_info_area").append("当前第"+pageinfo.pageNum+"页,总"+pageinfo.pages+"页,总"+pageinfo.total+"记录");
			//设置总记录数
			totalRecord=pageinfo.total;
			//设置当前页面数
			currentNum=pageinfo.pageNum;
		}
		function build_emps_table(result){
			//清空table表格
			$("#emps_tables tbody").empty();
			var emps=result.eMap.pageInfo.list;
			$.each(emps,function(index,item){
				var checkBoxTd=$("<td><input type='checkbox' class='check_item'></td>");
				var empIdTd=$("<td></td>").append(item.empId);
				var empNameTd=$("<td></td>").append(item.empName);
				var genderTd=$("<td></td>").append(item.gender=='M'?'男':'女');
				var eamilTd=$("<td></td>").append(item.email);
				var deptNameTd=$("<td></td>").append(item.department.deptName);
				var editBtn=$("<button></button>").addClass("btn btn-primary btn-sm edit_btn").append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("编辑");
				//给编辑按钮添加一个只定义的属性表示当前员工的id
				editBtn.attr("edit-id",item.empId);
				var delBtn=$("<button></button>").addClass("btn btn-danger btn-sm del_btn").append($("<span></span>").addClass("glyphicon glyphicon-trash")).append("删除");
				//给删除按钮添加一个只定义的属性表示当前员工的id
				delBtn.attr("delit-id",item.empId);
				var btnTd=$("<td></td>").append(editBtn).append(" ").append(delBtn);
				//append方法执行完成以后还是返回原来的元素
				$("<tr></tr>").append(checkBoxTd)
							  .append(empIdTd)
							  .append(empNameTd)
							  .append(genderTd)
							  .append(eamilTd)
							  .append(deptNameTd)
							  .append(btnTd)
							  .appendTo("#emps_tables tbody");
			});
		}
		function reset_form(ele){
			//清空表单数据
			$(ele)[0].reset();
			//清空样式
			$(ele).find("*").removeClass("has-error has-success");
			$(ele).find(".help-block").text("");
		}
		//点击新增按钮弹出模态框
		$("#emp_add_modal_btn").click(function(){
			//清除表单数据完整重置(表单数据重置,表单样式重置)
			reset_form("#empAddmodel form");
			//查找所有部门信息
			getDepts("#dept_add_select");
			//发生ajax请求,查出部门信息,显示在下拉列表中
			$("#empAddmodel").modal({
				backdrop:"static"
			});
		});
		//查询所有部门信息并显示在下拉列表中
		function getDepts(ele){
			//清空员工的下拉列表
			$(ele).empty();
			$.ajax({
				url:"${APP_PATH}/depts",
				type:"GET",
				success:function(result){
					console.log(result);
					$.each(result.eMap.depts,function(index,item){
						var optionEle=$("<option></option>").append(this.deptName).attr("value",this.deptId);
						optionEle.appendTo(ele);
					})
				}
			})
		}
		//校验表单数据
		function validate_add_form(){
			//1.拿到校验的数据,使用正则表达式
			//员工名称
			var empName=$("#empName_input").val();
			var regName=/(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
			if(!regName.test(empName)){
				//应该清空这个元素之前的样式
				show_validate_msg("#empName_input","error","用户名可以是2-5位或者6-16位英文和数字的组合");
				return false;
			}else{
				show_validate_msg("#empName_input","success","");
			}
			//2.校验邮箱信息
			var email=$("#email_add_input").val();
			var regEmail=/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
			if(!regEmail.test(email)){
				show_validate_msg("#email_add_input","error","邮箱格式不对！");
				return false;
			}else{
				show_validate_msg("#email_add_input","success","");
			}
			return true;
		}
		//校验状态
		function show_validate_msg(ele,status,msg){
			$(ele).parent().removeClass("has-success has-error");
			$(ele).next("span").text("");
			if("success"==status){
				$(ele).parent().addClass("has-success");
				$(ele).next("span").text(msg);
			}else if("error"==status){
				$(ele).parent().addClass("has-error");
				$(ele).next("span").text(msg);
			}
		}
		//校验用户名是否可用
		$("#empName_input").change(function(){
			var empName=this.value;
			//发送ajax请求校验用户名是否可用
			$.ajax({
				url:"${APP_PATH}/checkuser",
				data:"empName="+empName,
				success:function(result){
					console.log(result)
					if(result.code==100){
						show_validate_msg("#empName_input","success","用户名可用!!");
						$("#emp_save_btn").attr("ajax-va","success");
					}else{
						show_validate_msg("#empName_input","error",result.eMap.va_msg);
						$("#emp_save_btn").attr("ajax-va","error");
					}
				}
			})
		})
		//编辑按钮事件
		$(document).on("click",".edit_btn",function(){
			//1.查出部门信息
			getDepts("#dept_update_select");
			//2.查出员工信息
			getEmp($(this).attr("edit-id"));
			$("#emp_update_btn").attr("edit-id",$(this).attr("edit-id"));
			$("#empUpdatemodel").modal({
				backdrop:"static"
			});
		});
		//删除按钮事件
		$(document).on("click",".del_btn",function(){
			var empName=$(this).parents("tr").find("td:eq(2)").text();
			var empId=$(this).attr("delit-id");
			//1.弹出是否确认删除对话框
			if(confirm("确认删除【"+empName+"】")){
				//确定,发生ajax请求删除
				$.ajax({
					url:"${APP_PATH}/emp/"+empId,
					type:"DELETE",
					success:function(result){
						//回到本页面
						to_page(currentNum);
					}
				})
			}
		})
		//完成全选和全部选
		$("#check_all").click(function(){
			//attr用来获取自定义的属性值,使用prop获取原声的属性值
			$(".check_item").prop("checked",$(this).prop("checked"));
		})
		//给每一个单选的按钮添加一个事假
		$(document).on("click",".check_item",function(){
			//判断当前选中中的元素是否5个
			var flag=$(".check_item:checked").length==$(".check_item").length;
			$("#check_all").prop("checked",flag);
		})
		$("#emp_delete_all_btn").click(function(){
			var empNames="";
			var del_idstr="";
			$.each($(".check_item:checked"),function(index,item){
				empNames+=$(this).parents("tr").find("td:eq(2)").text()+",";
				del_idstr+=$(this).parents("tr").find("td:eq(1)").text()+"-";
			})
			//去除字符串中最后一个字符号
			empNames.substring(0,empNames.length-1)
			del_idstr.substring(0,empNames.length-1)
			if(confirm("确认删除【"+empNames+"】吗？")){
				//确定,发生ajax请求删除
				$.ajax({
					url:"${APP_PATH}/emp/"+del_idstr,
					type:"DELETE",
					success:function(result){
						//回到本页面
						to_page(currentNum);
					}
				})
			}
		})
		function getEmp(id){
			$.ajax({
				url:"${APP_PATH}/emp/"+id,
				type:"get",
				success:function(result){
					var empData=result.eMap.emp;
					$("#empName_update_static").text(empData.empName);
					$("#email_update_input").val(empData.email);
					$("#empUpdatemodel input[name=gender]").val([empData.gender]);
					$("#empUpdatemodel select").val([empData.dId]);
				}
			})
		}
		$("#emp_save_btn").click(function(){
			//1.模态框中填写的表单数据交给服务器进行保存
			//1.先对要提交给服务器的数据进行校验
			if(!validate_add_form()){
				return false;
			}
			//1.判断之前的ajax用户名校验是否成功,如果成功则继续执行
			if($(this).attr("ajax-va")=="error"){
				return false;
			}
			//2.发送ajax请求保存员工
			$.ajax({
				url:"${APP_PATH}/emp",
				type:"POST",
				data:$("#empAddmodel form").serialize(),
				success:function(result){
					if(result.code==100){
						//员工保存成功
						//1.关闭信息框
						$("#empAddmodel").modal("hide");
						//2.来到最后一页,显示刚才保存的数据
						//因为pageinfo如果超出他的最大页码就会默认显示最后一页,将总记录数设置为最后一页,因为总记录数一定比页码大
						to_page(totalRecord);
					}else{
						//有哪个字段的错误信息就显示哪个字段
						if(undefined !=result.eMap.FieldErrors.empName){
							show_validate_msg("#empName_input","error",result.eMap.FieldErrors.empName);
						}
						if(undefined !=result.eMap.FieldErrors.email){
							show_validate_msg("#email_add_input","error",result.eMap.FieldErrors.email);
						}
					}
				}
			})
		})
		$("#emp_update_btn").click(function(){
			//验证邮箱是否合法
			//1.校验邮箱信息
			var email=$("#email_update_input").val();
			var regEmail=/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
			if(!regEmail.test(email)){
				show_validate_msg("#email_update_input","error","邮箱格式不对！");
				return false;
			}else{
				show_validate_msg("#email_update_input","success","");
			}
			//2.发送ajax请求保存更新的员工数据
			$.ajax({
				url:"${APP_PATH}/emp/"+$(this).attr("edit-id"),
				type:"PUT",
				data:$("#empUpdatemodel form").serialize(),
				success:function(result){
					//1.关闭模态框
					$("#empUpdatemodel").modal("hide");
					//2.回到本页面
					to_page(currentNum);
				}
			})
		})
	</script>
</body>
</html>