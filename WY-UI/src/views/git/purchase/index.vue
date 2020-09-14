<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="采购原料名称" prop="purchaseName" label-width="100px">
        <el-input
          v-model="queryParams.purchaseName"
          placeholder="请输入采购原料名称"
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
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['purchase:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['purchase:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['purchase:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['enterprise:drinkMenu:import']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['purchase:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="purchaseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="采购原料名称" align="center" prop="purchaseName" />
      <el-table-column label="隶属店铺" align="center" prop="underShop" />
      <el-table-column label="采购量" align="center" prop="purchaseNum" />
      <el-table-column label="单位" align="center" prop="purchaseUnit" :formatter="unitFormat"/>
      <el-table-column label="单价" align="center" prop="unitPrice" />
      <el-table-column label="总价" align="center" prop="totalPrice" />
      <el-table-column label="采购员" align="center" prop="createBy" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['purchase:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['purchase:remove']"
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
        <el-form-item label="原料名称" prop="purchaseName">
          <el-input v-model="form.purchaseName" placeholder="请输入采购原料名称" />
        </el-form-item>
        <el-form-item label="采购量" prop="purchaseNum">
          <el-input v-model="form.purchaseNum" placeholder="请输入采购量" />
        </el-form-item>
        <el-form-item label="单位" prop="purchaseUnit">
          <el-select v-model="form.purchaseUnit" placeholder="请选择">
            <el-option
              v-for="dict in UnitOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="单价" prop="unitPrice">
          <el-input v-model="form.unitPrice" placeholder="请输入单价" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 采购清单导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px">
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

  import { listUseDept } from "@/api/system/dept";
  import { getToken } from "@/utils/auth";
  import { listPurchase, getPurchase, addPurchase, updatePurchase, delPurchase, exportPurchase, importTemplate } from "@/api/git/purchase";

    export default {
      name: "Purchase",
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
          // 日期范围
          dateRange: [],
          // 采购表格数据
          purchaseList: [],
          deptList: [],
          // 新增/修改弹出层标题
          title: "",
          // 是否显示新增/修改弹出层
          open: false,
          //隶属店铺数据字典
          shopOptions: [],
          //单位数据字典
          UnitOptions: [],
          // 查询参数
          queryParams: {
            pageNum: 1,
            pageSize: 10,
            purchaseName: undefined,
            underShop: undefined
          },
          upload: {
            // 是否显示弹出层（采购清单导入）
            open: false,
            // 弹出层标题（采购清单导入）
            title: "",
            // 是否禁用上传
            isUploading: false,
            // 设置上传的请求头部
            headers: { Authorization: "Bearer " + getToken() },
            // 上传的地址
            url: process.env.VUE_APP_BASE_API + "/purchase/importData"
          },
          form: {},
          // 表单校验
          rules: {
            purchaseName: [
              { required: true, message: "库存名称不能为空", trigger: "blur" }
            ],
            purchaseNum: [
              { required: true, message: "库存量不能为空", trigger: "blur" }
            ],
            purchaseUnit: [
              { required: true, message: "单位不能为空", trigger: "change" }
            ],
            unitPrice: [
              { required: true, message: "单价不能为空", trigger: "blur" }
            ]
          }
        };
      },
      created() {
        this.getList();
        this.getUseDeptList();
        this.getDicts("stock_unit").then(response => {
          this.UnitOptions = response.data;
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
        /** 导入按钮操作 */
        handleImport() {
          this.upload.title = "采购清单导入";
          this.upload.open = true;
        },
        /** 下载模板操作 */
        importTemplate() {
          importTemplate().then(response => {
            this.download(response.msg);
          });
        },
        // 文件上传中处理
        handleFileUploadProgress(event, file, fileList) {
          this.upload.isUploading = true;
        },
        // 文件上传成功处理
        handleFileSuccess(response, file, fileList) {
          this.upload.open = false;
          this.upload.isUploading = false;
          this.$refs.upload.clearFiles();
          this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
          this.getList();
        },
        // 提交上传文件
        submitFileForm() {
          this.$refs.upload.submit();
        },
        // 库存单位显示单位字典翻译
        unitFormat(row, column) {
          return this.selectDictLabel(this.UnitOptions, row.purchaseUnit);
        },
        /** 查询列表 */
        getList() {
          this.loading = true;
          listPurchase(this.queryParams).then(response => {
            this.purchaseList = response.rows;
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
            purchaseId: undefined,
            purchaseName: undefined,
            underShop: undefined,
            purchaseNum: undefined,
            purchaseUnit: undefined,
            unitPrice: undefined,
            totalPrice: undefined
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
          this.ids = selection.map(item => item.purchaseId)
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
          const purchaseId = row.purchaseId || this.ids
          getPurchase(purchaseId).then(response => {
            this.form = response.data;
            this.open = true;
            this.title = "修改菜单";
          });
        },
        /** 提交按钮 */
        submitForm: function() {
          this.$refs["form"].validate(valid => {
            if (valid) {
              this.form.totalPrice = (this.form.purchaseNum) * (this.form.unitPrice)
              if (this.form.purchaseId != undefined) {
                updatePurchase(this.form).then(response => {
                  if (response.code === 200) {
                    this.msgSuccess("修改成功");
                    this.open = false;
                    this.getList();
                  } else {
                    this.msgError(response.msg);
                  }
                });
              } else {
                addPurchase(this.form).then(response => {
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
          const purchaseIds = row.purchaseId || this.ids;
          this.$confirm('是否确认删除菜单编号为"' + purchaseIds + '"的数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function() {
            return delPurchase(purchaseIds);
          }).then(() => {
            this.getList();
            this.msgSuccess("删除成功");
          }).catch(function() {});
        },
        /** 导出按钮操作 */
        handleExport() {
          const queryParams = this.queryParams;
          this.$confirm('是否确认导出所有采购数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function() {
            return exportPurchase(queryParams);
          }).then(response => {
            this.download(response.msg);
          }).catch(function() {});
        }
      }
    };
</script>
