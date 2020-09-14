<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="隶属店铺" prop="underShop">
        <el-select
          v-model="queryParams.underShop"
          placeholder="隶属店铺"
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="item in deptList"
            :key="item.deptId"
            :label="item.deptName"
            :value="item.deptName"/>
        </el-select>
      </el-form-item>
      <el-form-item label="时间单位" prop="timeUtil">
        <el-select v-model="queryParams.timeUtil" placeholder="请选择">
          <el-option
            v-for="dict in timeUtilOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="dateRange"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['turnover:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="turnoverList">
      <el-table-column label="隶属店铺" align="center" prop="underShop" />
      <el-table-column label="统计时间" align="center" prop="statisticsTime" />
      <el-table-column label="营业额" align="center" prop="money" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <template>
      <div class="h-line">
        <ve-line
          :data="chartData"
          :settings="chartSettings"
        ></ve-line>
      </div>
    </template>
  </div>
</template>

<script>
  import { listUseDept } from "@/api/system/dept";
  import { listTurnover, exportTurnover } from "@/api/statistics/turnover";

    export default {
      name: "Turnover",
      data() {
        return {
          // 总条数
          total: 0,
          // 日期范围
          dateRange: [],
          // 营业额数据
          turnoverList: [],
          deptList: [],
          timeUtilOptions: [],
          // 查询参数
          queryParams: {
            pageNum: 1,
            pageSize: 10,
            underShop: '南京总店',
            timeUtil: undefined,
            statisticsTime: undefined
          },
          chartData: {
            columns: ['statisticsTime','money'],
            rows: []
          },
          chartSettings: {
            labelMap:{
              statisticsTime:"日期",
              money: "营业额"
            },
            yAxisName: ['元']
          },
        };
      },
      created() {
        //时间选择器默认数据开始
        var now = new Date();
        var day = now.getDate();
        var year = now.getFullYear(); //得到当前年份
        var month = now.getMonth(); //默认得到月份是上一个月，如果当前是3月，这个值为2月
        if(month ==12)	//如果当前是1月，则获取到的数据为12月，年份减一
          year=year -1;
        var nextMonth=month+1;//其实就是当前月份
        month = month.toString().padStart(2, "0");	//当小于10时，显示为01.02.03
        nextMonth = nextMonth.toString().padStart(2, "0");
        this.startdate = `${year}-${nextMonth}-01`;//拼接日期
        this.enddate = `${year}-${nextMonth}-${day}`;//拼接日期
        this.dateRange[0]=this.startdate;
        this.dateRange[1]=this.enddate;
        //时间选择器默认数据结束
        this.getList();
        this.getUseDeptList();
        this.getDicts("time_util").then(response => {
          this.timeUtilOptions = response.data;
        });
      },
      methods: {
        /** 查询店铺列表 */
        getUseDeptList() {
          this.loading = true;
          listUseDept().then(response => {
              this.deptList = response.data;
              this.loading = false;
            }
          );
        },
        /** 查询列表 */
        getList() {
          listTurnover(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
            this.turnoverList = response.rows;
            this.chartData.rows = response.rowsBack;
            this.total = response.total;
          });
        },
        /** 重置按钮操作 */
        resetQuery() {
          this.dateRange = [];
          this.resetForm("queryForm");
          this.handleQuery();
        },
        /** 搜索按钮操作 */
        handleQuery() {
          this.queryParams.pageNum = 1;
          this.getList();
        },
        /** 导出按钮操作 */
        handleExport() {
          const queryParams = this.queryParams;
          this.$confirm('是否确认导出所有营业额数据?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function() {
            return exportTurnover(queryParams);
          }).then(response => {
            this.download(response.msg);
          }).catch(function() {});
        }
      }
    };
</script>
