<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="隶属店铺" prop="underShop">
        <el-select
          v-model="queryParams.underShop"
          placeholder="隶属店铺"
          clearable
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
      <el-form-item label="支付类型" prop="payType">
        <el-input
          v-model="queryParams.payType"
          placeholder="请输入支付类型"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="支付状态" prop="payStatus">
        <el-input
          v-model="queryParams.payStatus"
          placeholder="请输入支付状态"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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

    <el-table v-loading="loading" :data="orderList">
      <el-table-column label="流水号" align="center" prop="orderNo" />
      <el-table-column label="订单内容" align="center" prop="content" :show-overflow-tooltip="true"/>
      <el-table-column label="总价" align="center" prop="totalPrice" />
      <el-table-column label="支付方式" align="center" prop="payType" />
      <el-table-column label="支付状态" align="center" prop="payStatus" />
      <el-table-column label="隶属店铺" align="center" prop="underShop" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

  </div>
</template>

<script>
  import { listUseDept } from "@/api/system/dept";
  import { listOrder } from "@/api/order/order";

    export default {
      name: "listOrder",
      data() {
        return {
          // 日期范围
          dateRange: [],
          // 遮罩层
          loading: true,
          // 总条数
          total: 0,
          // 订单数据
          orderList: [],
          deptList: [],
          // 查询参数
          queryParams: {
            pageNum: 1,
            pageSize: 10,
            payStatus: undefined,
            payType: undefined,
            underShop: undefined,
            createTime: undefined
          },
          form: {}
        };
      },
      created() {
        this.getList();
        this.getUseDeptList();
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
          this.loading = true;
          listOrder(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
            this.orderList = response.rows;
            this.total = response.total;
            this.loading = false;
          });
        },
        /** 搜索按钮操作 */
        handleQuery() {
          this.queryParams.pageNum = 1;
          this.getList();
        },
        /** 重置按钮操作 */
        resetQuery() {
          this.dateRange = [];
          this.resetForm("queryForm");
          this.handleQuery();
        },
      }
    };
</script>
