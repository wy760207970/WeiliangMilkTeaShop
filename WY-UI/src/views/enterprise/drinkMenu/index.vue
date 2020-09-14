<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="饮品名称" prop="menuName">
        <el-input
          v-model="queryParams.menuName"
          placeholder="请输入饮品名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['enterprise:drinkMenu:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['enterprise:drinkMenu:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['enterprise:drinkMenu:remove']"
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
          v-hasPermi="['enterprise:drinkMenu:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="menuList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="饮品名称" align="center" prop="menuName" />
      <el-table-column label="饮品温度" align="center" prop="temp" />
      <el-table-column label="饮品规格" align="center" prop="specs" />
      <el-table-column label="饮品价格" align="center" prop="price" />
      <el-table-column label="饮品类别" align="center" prop="classify" :formatter="classifyFormat"/>
      <el-table-column label="显示排序" align="center" prop="orderNum" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['enterprise:drinkMenu:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['enterprise:drinkMenu:remove']"
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
        <el-form-item label="饮品名称" prop="menuName">
          <el-input v-model="form.menuName" placeholder="请输入菜单名称" />
        </el-form-item>
        <el-form-item label="饮品温度" prop="temp">
          <el-checkbox-group v-model="form.temp">
            <el-checkbox label="冰"></el-checkbox>
            <el-checkbox label="常温"></el-checkbox>
            <el-checkbox label="热"></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="饮品规格" prop="specs">
          <el-checkbox-group v-model="form.specs">
            <el-checkbox label="小杯"></el-checkbox>
            <el-checkbox label="中杯"></el-checkbox>
            <el-checkbox label="大杯"></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="饮品价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入价格(多个价格用/隔断)" />
        </el-form-item>
        <el-form-item label="饮品类别">
          <el-select v-model="form.classify" placeholder="请选择">
            <el-option
              v-for="dict in classifyOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="显示排序" prop="orderNum">
          <el-input-number v-model="form.orderNum" controls-position="right" :min="0" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 菜单导入对话框 -->
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

  import { getToken } from "@/utils/auth";
  import { listMenu, getMenu, addMenu, updateMenu, delMenu, exportMenu, importTemplate } from "@/api/enterprise/drinkMenu";

    export default {
      name: "drinkMenu",
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
          menuList: [],
          // 新增/修改弹出层标题
          title: "",
          // 是否显示新增/修改弹出层
          open: false,
          classifyOptions: [],
          // 查询参数
          queryParams: {
            pageNum: 1,
            pageSize: 10,
            menuName: undefined,
            classify: undefined
          },
          form: {},
          // 菜单导入参数
          upload: {
            // 是否显示弹出层（菜单导入）
            open: false,
            // 弹出层标题（菜单导入）
            title: "",
            // 是否禁用上传
            isUploading: false,
            // 设置上传的请求头部
            headers: { Authorization: "Bearer " + getToken() },
            // 上传的地址
            url: process.env.VUE_APP_BASE_API + "/enterprise/drinkMenu/importData"
          },
          // 表单校验
          rules: {
            menuName: [
              { required: true, message: "饮品名称不能为空", trigger: "blur" }
            ],
            temp: [
              { required: true, message: "饮品温度不能为空", trigger: "blur" }
            ],
            specs: [
              { required: true, message: "饮品规格不能为空", trigger: "blur" }
            ],
            price: [
              { required: true, message: "饮品价格不能为空", trigger: "blur" }
            ],
            classify: [
              { required: true, message: "饮品类别不能为空", trigger: "change" }
            ]
          }
        };
      },
      created() {
        this.getList();
        this.getDicts("drink_menu_classify").then(response => {
          this.classifyOptions = response.data;
        });
      },
      methods: {
        /** 导入按钮操作 */
        handleImport() {
          this.upload.title = "菜单导入";
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
        // 饮品菜单显示类别字典翻译
        classifyFormat(row, column) {
          return this.selectDictLabel(this.classifyOptions, row.classify);
        },
        /** 查询列表 */
        getList() {
          this.loading = true;
          listMenu(this.queryParams).then(response => {
            this.menuList = response.rows;
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
            menuId: undefined,
            menuName: undefined,
            temp: [],
            specs: [],
            price: undefined,
            classify: undefined,
            orderNum: undefined
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
          this.ids = selection.map(item => item.menuId)
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
          const menuId = row.menuId || this.ids
          getMenu(menuId).then(response => {
            this.form = response.data;
            let specs=this.form.specs.split("/");
            let temp=this.form.temp.split("/");
            this.form.specs = specs;
            this.form.temp = temp;
            this.open = true;
            this.title = "修改菜单";
          });
        },
        /** 提交按钮 */
        submitForm: function() {
          this.$refs["form"].validate(valid => {
            if (valid) {
              if (this.form.menuId != undefined) {
                //Array转String
                let specs="";
                for (let i=0;i<this.form.specs.length;i++){
                  specs += this.form.specs[i] + "/";
                }
                specs=specs.substring(0,specs.length-1);
                this.form.specs = specs;
                let temp="";
                for (let i=0;i<this.form.temp.length;i++){
                  temp += this.form.temp[i] + "/";
                }
                temp=temp.substring(0,temp.length-1);
                this.form.temp = temp;
                //更新
                updateMenu(this.form).then(response => {
                  if (response.code === 200) {
                    this.msgSuccess("修改成功");
                    this.open = false;
                    this.getList();
                  } else {
                    this.msgError(response.msg);
                  }
                });
              } else {
                //Array转String
                let specs="";
                for (let i=0;i<this.form.specs.length;i++){
                  specs += this.form.specs[i] + "/";
                }
                specs=specs.substring(0,specs.length-1);
                this.form.specs = specs;
                let temp="";
                for (let i=0;i<this.form.temp.length;i++){
                  temp += this.form.temp[i] + "/";
                }
                temp=temp.substring(0,temp.length-1);
                this.form.temp = temp;
                //新增
                addMenu(this.form).then(response => {
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
          const menuIds = row.menuId || this.ids;
          this.$confirm('是否确认删除菜单编号为"' + menuIds + '"的数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function() {
            return delMenu(menuIds);
          }).then(() => {
            this.getList();
            this.msgSuccess("删除成功");
          }).catch(function() {});
        },
        /** 导出按钮操作 */
        handleExport() {
          const queryParams = this.queryParams;
          this.$confirm('是否确认导出所有菜单数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function() {
            return exportMenu(queryParams);
          }).then(response => {
            this.download(response.msg);
          }).catch(function() {});
        }
      }
    };
</script>
