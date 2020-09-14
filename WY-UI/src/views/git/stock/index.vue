<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="库存名称" prop="stockName">
        <el-input
          v-model="queryParams.stockName"
          placeholder="请输入库存名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
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
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['stock:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['stock:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['stock:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['stock:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="stockList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="库存名称" align="center" >
        <template slot-scope="scope">
          <router-link :to="{ name:'StockDetail',params: { stockId: scope.row.stockId}}" class="link-type">
            <span>{{ scope.row.stockName }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="隶属店铺" align="center" prop="underShop" />
      <el-table-column label="库存量" align="center" prop="stockNum" />
      <el-table-column label="库存单位" align="center" prop="stockUnit" :formatter="unitFormat"/>
      <el-table-column label="更新库存时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="负责人" align="center" prop="createBy" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['stock:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['stock:remove']"
          >删除</el-button>
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

    <!-- 添加或修改岗位对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="库存名称" prop="stockName">
          <el-input v-model="form.stockName" placeholder="请输入库存名称" />
        </el-form-item>
        <el-form-item label="库存单位" prop="stockUnit">
          <el-select v-model="form.stockUnit" placeholder="请选择">
            <el-option
              v-for="dict in stockUnitOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { listUseDept } from "@/api/system/dept";
  import { listStock, getStock, addStock, updateStock, delStock, exportStock } from "@/api/git/stock";

    export default {
      name: "Stock",
      data() {
        return {
          // 遮罩层
          loading: true,
          // 选中数组
          ids: [],
          // 非单个禁用
          single: true,
          // 非多个禁用
          multiple: true,
          // 总条数
          total: 0,
          // 岗位表格数据
          stockList: [],
          deptList: [],
          // 新增/修改弹出层标题
          title: "",
          // 是否显示新增/修改弹出层
          open: false,
          //库存单位数据字典
          stockUnitOptions: [],
          // 查询参数
          queryParams: {
            pageNum: 1,
            pageSize: 10,
            stockName: undefined,
            underShop: undefined
          },
          form: {},
          // 表单校验
          rules: {
            stockName: [
              { required: true, message: "库存名称不能为空", trigger: "blur" }
            ],
            stockUnit: [
              { required: true, message: "库存单位不能为空", trigger: "change" }
            ]
          }
        };
      },
      created() {
        this.getList();
        this.getUseDeptList();
        this.getDicts("stock_unit").then(response => {
          this.stockUnitOptions = response.data;
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
        // 库存单位显示单位字典翻译
        unitFormat(row, column) {
          return this.selectDictLabel(this.stockUnitOptions, row.stockUnit);
        },
        /** 查询列表 */
        getList() {
          this.loading = true;
          listStock(this.queryParams).then(response => {
            this.stockList = response.rows;
            this.total = response.total;
            this.loading = false;
          });
        },
        // 取消按钮
        cancel() {
          this.open = false;
          this.reset();
        },
        // 表单重置
        reset() {
          this.form = {
            stockId: undefined,
            stockName: undefined,
            stockUnit: undefined
          };
          this.resetForm("form");
        },
        /** 搜索按钮操作 */
        handleQuery() {
          this.queryParams.pageNum = 1;
          this.getList();
        },
        /** 重置按钮操作 */
        resetQuery() {
          this.resetForm("queryForm");
          this.handleQuery();
        },
        // 多选框选中数据
        handleSelectionChange(selection) {
          this.ids = selection.map(item => item.stockId)
          this.single = selection.length!=1
          this.multiple = !selection.length
        },
        /** 新增按钮操作 */
        handleAdd() {
          this.reset();
          this.open = true;
          this.title = "添加菜单";
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
          this.reset();
          const stockId = row.stockId || this.ids
          getStock(stockId).then(response => {
            this.form = response.data;
            this.open = true;
            this.title = "修改菜单";
          });
        },
        /** 提交按钮 */
        submitForm: function() {
          this.$refs["form"].validate(valid => {
            if (valid) {
              if (this.form.stockId != undefined) {
                updateStock(this.form).then(response => {
                  if (response.code === 200) {
                    this.msgSuccess("修改成功");
                    this.open = false;
                    this.getList();
                  } else {
                    this.msgError(response.msg);
                  }
                });
              } else {
                addStock(this.form).then(response => {
                  if (response.code === 200) {
                    this.msgSuccess("新增成功");
                    this.open = false;
                    this.getList();
                  } else {
                    this.msgError(response.msg);
                  }
                });
              }
            }
          });
        },
        /** 删除按钮操作 */
        handleDelete(row) {
          const stockIds = row.stockId || this.ids;
          this.$confirm('是否确认删除菜单编号为"' + stockIds + '"的数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function() {
            return delStock(stockIds);
          }).then(() => {
            this.getList();
            this.msgSuccess("删除成功");
          }).catch(function() {});
        },
        /** 导出按钮操作 */
        handleExport() {
          const queryParams = this.queryParams;
          this.$confirm('是否确认导出所有库存数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function() {
            return exportStock(queryParams);
          }).then(response => {
            this.download(response.msg);
          }).catch(function() {});
        }
      }
    };
</script>
