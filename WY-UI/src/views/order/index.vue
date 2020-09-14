<template>
  <div class="app-container">
    <el-row style="margin-bottom: 10px">
      <el-tag>选择饮品</el-tag>
    </el-row>
    <div style="margin-bottom: 20px">
      <el-button v-for="(item, index) in menuList" :key="index" @click="handleAdd(item)" style="margin-top: 5px">{{item.menuName}}</el-button>
    </div>

    <el-form ref="order" :model="order" :rules="rules2" label-width="80px">
      <el-form-item label="支付方式" prop="payType">
        <el-radio-group v-model="order.payType">
          <el-radio label="支付宝"></el-radio>
          <el-radio label="微信"></el-radio>
          <el-radio label="现金"></el-radio>
          <el-radio label="其他"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="支付状态" prop="payStatus">
        <el-radio-group v-model="order.payStatus">
          <el-radio label="已支付"></el-radio>
          <el-radio label="未支付"></el-radio>
          <el-radio label="其他"></el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitOrder">确 定</el-button>
      <el-button @click="resetAll">重 置</el-button>
    </div>


    <el-dialog :title="title" :visible.sync="open" width="500px">
      <el-form ref="form" :model="orderItem" :rules="rules" label-width="80px">
        <el-form-item label="温度" prop="temp">
          <el-radio-group v-model="orderItem.temp">
            <el-radio v-for="(item, index) in temp" :key="index" :label="item"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="规格" prop="specs">
          <el-radio-group v-model="orderItem.specs">
            <el-radio v-for="(item, index) in specs" :key="index" :label="item" @change="getSpecs(index)"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="数量" prop="orderCount">
          <el-input-number v-model="orderItem.orderCount" controls-position="right" :min="0" />
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

  import { listMenu } from "@/api/enterprise/drinkMenu";
  import { addOrderItem, addOrder, listOrderItem } from "@/api/order/order";

    export default {
      name: "Order",
      data() {
        return {
          totalPrice: 0,
          temp: [],
          specs: [],
          drinkPrice: [],
          specsIndex: null,
          orderNo:null,
          // 菜单数据
          menuList: [],
          // 订单商品数据
          orderItemList: [],
          //订单
          order: {},
          //订单商品
          orderItem: {},
          // 新增/修改弹出层标题
          title: "",
          // 是否显示新增/修改弹出层
          open: false,
          // 表单校验
          rules: {
            temp: [
              { required: true, message: "饮品温度不能为空", trigger: "change" }
            ],
            specs: [
              { required: true, message: "饮品规格不能为空", trigger: "change" }
            ],
            orderCount: [
              { required: true, message: "库存量不能为空", trigger: "blur" }
            ]
          },
          rules2: {
            payType: [
              { required: true, message: "支付方式不能为空", trigger: "change" }
            ],
            payStatus: [
              { required: true, message: "支付状态不能为空", trigger: "change" }
            ]
          }
        };
      },
      created() {
        this.resetOrder();
        this.getList();
      },
      methods: {
        getOrderItemList() {
          var orderNo = this.orderNo;
          listOrderItem(orderNo).then(response => {
            this.orderItemList = response.rows;
          });
        },
        submitOrder: function() {
          var orderNo = this.orderNo;
          listOrderItem(orderNo).then(response => {
            this.orderItemList = response.rows;
            if (this.orderItemList.length>0){
              var totalPrice = 0;
              for (var i=0;i< this.orderItemList.length;i++){
                totalPrice += (this.orderItemList[i].drinkPrice) * (this.orderItemList[i].orderCount);
              }
              this.totalPrice = totalPrice;
              var data = {
                orderNo: this.orderNo,
                payType: this.order.payType,
                payStatus: this.order.payStatus,
                totalPrice: this.totalPrice
              }
              this.$refs["order"].validate(valid => {
                if (valid) {
                  addOrder(data).then(response => {
                    if (response.code === 200) {
                      this.msgSuccess("新增成功");
                      this.resetAll()
                    } else {
                      this.msgError(response.msg);
                    }
                  });
                }
              });
            }else {
              this.msgError("请选择商品");
            }
          });
        },
        resetAll(){
          this.resetOrder();
          this.reset();
          this.order = {
            payType: undefined,
            payStatus: undefined
          };
        },
        resetOrder(){
          this.orderNo = this.randomNumber();
        },
        //根据当前时间和随机数生成流水号
        randomNumber() {
          var todayDate=new Date();
          var year=todayDate.getFullYear();
          var date=todayDate.getDate();
          var month=todayDate.getMonth()+1;
          var hour=todayDate.getHours();
          var mininutes=todayDate.getMinutes();
          var seconds=todayDate.getSeconds();
          var ran=Math.round((Math.random())*10000);
          return year+""+date+""+month+""+hour+""+mininutes+""+seconds+""+ran;
        },
        getSpecs(index) {
          this.specsIndex = index
        },
        /** 新增按钮操作 */
        handleAdd(item) {
          this.reset();
          this.open = true;
          this.title = "添加商品";
          this.orderItem.drinkName = item.menuName;
          let temp = item.temp.split("/");
          this.temp = temp;
          let specs = item.specs.split("/");
          this.specs = specs;
          let drinkPrice = item.price.split("/");
          this.drinkPrice = drinkPrice;
        },
        /** 查询列表 */
        getList() {
          listMenu().then(response => {
            this.menuList = response.rows;
          });
        },
        // 取消按钮
        cancel() {
          this.open = false;
          this.reset();
        },
        // 表单重置
        reset() {
          this.orderItem = {
            orderItemId: undefined,
            drinkPrice: undefined,
            orderCount: undefined
          };
          this.resetForm("form");
        },
        /** 重置按钮操作 */
        resetQuery() {
          this.resetForm("queryForm");
          this.handleQuery();
        },
        /** 提交按钮 */
        submitForm: function() {
          this.$refs["form"].validate(valid => {
            if (valid) {
              this.orderItem.orderNo = this.orderNo
              this.orderItem.drinkPrice = this.drinkPrice[this.specsIndex];
              addOrderItem(this.orderItem).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("添加成功");
                  this.open = false;
                } else {
                  this.msgError(response.msg);
                }
              });
            }
          });
        }
      }
    };
</script>
