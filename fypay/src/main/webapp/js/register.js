/**
 * Created by Sunny on 17/8/26.
 */

var BasePubURL = "https://jerp.skydispark.com:62221/skyderp/pub?action=";

//经营类型一级目录
var businessType1Data = ["企业","个体工商户","事业单位"];

// 联系人类型
var contactPersonTypeData = [
        {"name":"法人","type_name":"LEGAL_PERSON"},{"name":"实际控制人","type_name":"CONTROLLER"},{"name":"代理人","type_name":"AGENT"},{"name":"其他","type_name":"OTHER"}
    ];
var businessLicenseTypeData = [
    {"name":"营业执照","type_name":"NATIONAL_LEGAL"},{"name":"营业执照多证合一","type_name":"NATIONAL_LEGAL_MERGE"},{"name":"事业单位法人证书","type_name":"INST_RGST_CTF"}
];

//<!--注册基本信息-->
var contactInfo =  new Vue ({
    el:'#contactInfo',
    data:{
        BIZCATEGORY:"0",//*经营类型
        BIZCATEGORY1:businessType1Data,//经营类型1数据
        BIZCATEGORY2:[],//经营类型2数据
        BIZCATEGORY3:[],//经营类型3数据
        businessType1:"0",//经营类型1
        businessType2:"0",//经营类型2
        contactName:"",//* 联系人名称
        contactPhone:"",//* 联系人手机号
        contactIdCard:"",//* 联系人身份证号
        contactPersonType:"",//* 联系人类型
        contactEmail:"",//* 联系人邮箱
        contactPersonTypeData:contactPersonTypeData,//联系人类型数据
        contactPersonTypeErrorTick:false//联系人类型数据错误提示标志
    },
    computed:{
        validContactPhone:function(){
            return {
                'errorInputTick':!validPhone(this.contactPhone) && this.contactPhone.length>0
            }
        },
        showContactPhoneErrTick:function () {
            return !validPhone(this.contactPhone) && this.contactPhone.length>0
        },
        validContactIDCard:function(){
            return {
                'errorInputTick':!validIDCard(this.contactIdCard) && this.contactIdCard.length>0
            }
        },
        showContactIdCardErrTick:function () {
            return !validIDCard(this.contactIdCard) && this.contactIdCard.length>0
        }
    },
    watch:{
        contactPersonType:function (newContactPersonType) {
            this.contactPersonTypeErrorTick = newContactPersonType === "";
        },
        businessLicenseType:function (newBusinessLicenseType) {
            this.businessLicenseTypeErrorTick = newBusinessLicenseType === "";
        },
        businessType1:function (newBusinessType1) {
            this.BIZCATEGORY2 = get2Cate(newBusinessType1);
            this.BIZCATEGORY3 = [];
            this.businessType2 = "0";
            this.BIZCATEGORY = "0";
        },
        businessType2:function (newBusinessType2) {
            this.BIZCATEGORY3 = get3Cate(this.businessType1,newBusinessType2);
            this.BIZCATEGORY = "0";
        }
    }

});

//<!--商户信息-->
var merchantInfo = new Vue({
    el:"#merchantInfo",
    data:{
        businessLicenseTypeData:businessLicenseTypeData,//证件类型数据
        province:"0",//* 商户地址省
        city:"0",//* 商户地址市
        district:"0",//* 商户地址区
        address:"",//* 详细地址
        cities:[],//商户地址的对应省的市列表
        counties:[],//商户地址的对应市的县\区列表
        fullName:"",//* 商户全称
        shortName:"",//* 商户简称
        servicePhone:"",//* 客服电话
        businessLicense:"",//* 证件编号
        businessLicenseType:"",//* 证件类型
        businessLicenseTypeErrorTick:false,//证件类型错误提示标志
        provinceErrorTick:false,//商户地址省错误提示标志
        cityErrorTick:false,//商户地址市错误提示标志
        districtErrorTick:false//商户地址区错误提示标志
    },
    watch:{
        province:function (newProvice) {
            this.counties = [];
            this.cities = getCitiesWithType(newProvice);
            this.provinceErrorTick = newProvice == "0";
            this.city = "0";
        },
        city:function (newCity) {
            this.counties = getCounties(newCity);
            this.cityErrorTick = newCity == "0";
            this.district = "0";
        },
        district:function (newDistrict) {
            this.districtErrorTick = newDistrict == "0";
        },
        businessLicenseType:function (newBusinessLicenseType) {
            uploadImgs.showZZJGDMZZP = newBusinessLicenseType != "NATIONAL_LEGAL_MERGE";
        }
    },
    methods:{
        getProvinces:function () {
            var provinces = [];
            for(var i = 0; i < areasForSubmit.length ; i++){
                if(!containsValue(provinces,"provinceName",areasForSubmit[i].provinceName)){
                    provinces.push(areasForSubmit[i]);
                }
            }

            return provinces;
        }
    }
});

//<!--注册结算账户-->
var bankCardInfo = new Vue({
    el:"#bankCardInfo",
    data:{
        province:"0",//结算账户开户省
        city:"0",// 结算账户开户市
        cities:[],// 省对应的市列表
        bankCode:"0",//开户银行对应的查询code
        banks:banks,//所有的开户银行
        branchBankList:[],//返回的所筛选的开户支行数组
        bankAccountAddress:"",//查询的开户支行名称
        branchBanks:[],//所选的开户银行下的开户支行
        provinceErrorTick:false,//商户地址省错误提示标志
        cityErrorTick:false,//商户地址市错误提示标志
        bankCodeErrorTick:false,//选择开户银行提示标志

        bankAccountName:"",//* 开户人姓名
        bankAccountNo:"",//* 银行账号
        idCard:"",//* 开户人身份证号码
        quaryBranchBank:"",//输入的查询支行信息的内容
        remitType:"1",//* 资金结算成本 1=T1 资金结算成本 0=D0
        bankAccountType:"2",//* 账户类型 1.对私账户 2.对公账户
        branchBank:"0",//* 选中的开户支行 里面有开户行地址：bankAccountAddress 银行联行号：bankAccountLineNo
        branchBankErrorTick:false//选中的开户支行提示标志
    },
    watch:{
        province:function (newProvice) {
            this.cities = cityjsonList[newProvice];
            this.city = "0";
            this.provinceErrorTick = newProvice == "0";
        },
        city:function (newCity) {
            this.cityErrorTick = newCity == "0";
        },
        bankCode:function (newBankCode) {
            this.bankCodeErrorTick = newBankCode == "0";
            qieryBranckBankList();
            this.branchBank = "0";

        },
        branchBank:function (newBranchBank) {
            this.branchBankErrorTick = newBranchBank == "0";
        },
        bankAccountType:function (newBankAccountType) {
            uploadImgs.showJskzp = newBankAccountType == "1";
        }

    },
    computed:{
        showProviceInputTick:function(){
            return {
                uninputTick:this.province.length == 0 || this.province === "选择开户省份",
            }
        },
        showCityInputTick:function(){
            return {
                uninputTick:this.city.length == 0,
            }
        },
        //验证开户身份证号
        validIdCard:function () {
            return {
                'errorInputTick':!validIDCard(this.idCard) && this.idCard.length>0
            }
        },
        //展示开户身份证号错误提示
        showIdCardErrorTick:function () {
            return !validIDCard(this.idCard) && this.idCard.length>0
        }
    },
    methods:{
        //返回结算账户开户省列表
        getProvinces:function () {
            return provinceList;
        }
    }
});


// 营业执照和证件
var uploadImgs =  new Vue({
    el:'#uploadImgs',
    data:{
        imageyyzz:{
            image:"",
            imageName:""
        },//*营业执照
        imagezzjg:{
            image:"",
            imageName:""
        },//*组织机构
        imagefrsfza:{
            image:"",
            imageName:""
        },//*法人身份证正面
        imagefrsfzb:{
            image:"",
            imageName:""
        },//*法人身份证反面
        imagejskcyrsfza:{
            image:"",
            imageName:""
        },//*结算卡持有人身份证正面
        imagejskcyrsfzb:{
            image:"",
            imageName:""
        },//*结算卡持有人身份证反面
        imagedpzpmt:{
            image:"",
            imageName:""
        },//*店铺照片（门头）
        imagedpzpsyt:{
            image:"",
            imageName:""
        },//*店铺照片（收银台）
        imagedpzpjycs:{
            image:"",
            imageName:""
        },//*店铺照片（经营场所）
        imagejskzp:{
            image:"",
            imageName:""
        },//*结算卡照片
        showJskzp:false,//是否展示输入结算卡照片区域（对公账户不需要提供，对私账户需要提供）
        showZZJGDMZZP:true,//是否展示组织机构代码证照片（三证合一不用提供）
    },
    methods:{
        addPic:function(e){

            e.preventDefault();
            var index = $(e.target).data("index");

            if (index == 1){
                $('#imageyyzzCapture').val('');
                $('#imageyyzzCapture').trigger('click');
            }else if(index == 2){
                $('#imagefrsfzaCapture').val('');
                $('#imagefrsfzaCapture').trigger('click');
            }else if(index == 3){
                $('#imagefrsfzbCapture').val('');
                $('#imagefrsfzbCapture').trigger('click');
            }else if(index == 4){
                $('#imagezzjgCapture').val('');
                $('#imagezzjgCapture').trigger('click');
            }else if(index == 5){
                $('#imagejskcyrsfzaCapture').val('');
                $('#imagejskcyrsfzaCapture').trigger('click');
            }else if(index == 6){
                $('#imagejskcyrsfzbCapture').val('');
                $('#imagejskcyrsfzbCapture').trigger('click');
            }else if(index == 7){
                $('#imagedpzpmtCapture').val('');
                $('#imagedpzpmtCapture').trigger('click');
            }else if(index == 8){
                $('#imagedpzpsytCapture').val('');
                $('#imagedpzpsytCapture').trigger('click');
            }else if(index == 9){
                $('#imagedpzpjycsCapture').val('');
                $('#imagedpzpjycsCapture').trigger('click');
            }else if(index == 10){
                $('#imagejskzpCapture').val('');
                $('#imagejskzpCapture').trigger('click');
            }
            return false;
        },
        onFileChange:function(e) {
            var files = e.target.files || e.dataTransfer.files;
            if (!files.length)return;
            var index = $(e.target).data("index");

            this.createImage(index,files);
        },
        createImage:function(index,file) {
            if(typeof FileReader==='undefined'){
                alert('您的浏览器不支持图片上传，请升级您的浏览器');
                return false;
            }
            var image = new Image();
            var vm = this;
            var leng=file.length;
            for(var i=0;i<leng;i++){
                var reader = new FileReader();
                reader.readAsDataURL(file[i]);
                reader.onload =function(e){
                    var image = e.target.result;
                    uploadImage(image,index);
                };
            }
        },
        delImage:function(index){
            var vm = this;
            if(index == 1){
                vm.imageyyzz.image = vm.imageyyzz.imageName="";
            }else if(index == 2){
                vm.imagefrsfza.image = vm.imagefrsfza.imageName ="";
            }else if(index == 3){
                vm.imagefrsfzb.image = vm.imagefrsfzb.imageName ="";
            }else if(index == 4){
                vm.imagezzjg.image = vm.imagezzjg.imageName ="";
            }else if(index == 5){
                vm.imagejskcyrsfza.image = vm.imagejskcyrsfza.imageName ="";
            }else if(index == 6){
                vm.imagejskcyrsfzb.image = vm.imagejskcyrsfzb.imageName ="";
            }else if(index == 7){
                vm.imagedpzpmt.image = vm.imagedpzpmt.imageName ="";
            }else if(index == 8){
                vm.imagedpzpsyt.image = vm.imagedpzpsyt.imageName ="";
            }else if(index == 9){
                vm.imagedpzpjycs.image = vm.imagedpzpjycs.imageName ="";
            }else if(index == 10){
                vm.imagejskzp.image = vm.imagejskzp.imageName ="";
            }
        },
        imageScale:function (image) {
            return image;
        },
        setImageUrlName:function (image,index,imageName) {
            var vm = this;
            if(index == 1){
                vm.imageyyzz.image= image;
                vm.imageyyzz.imageName = imageName;
            }else if(index == 2){
                vm.imagefrsfza.image= image;
                vm.imagefrsfza.imageName = imageName;
            }else if(index == 3){
                vm.imagefrsfzb.image= image;
                vm.imagefrsfzb.imageName = imageName;
            }else if(index == 4){
                vm.imagezzjg.image= image;
                vm.imagezzjg.imageName = imageName;
            }else if(index == 5){
                vm.imagejskcyrsfza.image= image;
                vm.imagejskcyrsfza.imageName = imageName;
            }else if(index == 6){
                vm.imagejskcyrsfzb.image= image;
                vm.imagejskcyrsfzb.imageName = imageName;
            }else if(index == 7){
                vm.imagedpzpmt.image= image;
                vm.imagedpzpmt.imageName = imageName;
            }else if(index == 8){
                vm.imagedpzpsyt.image= image;
                vm.imagedpzpsyt.imageName = imageName;
            }else if(index == 9){
                vm.imagedpzpjycs.image= image;
                vm.imagedpzpjycs.imageName = imageName;
            }else if(index == 10){
                vm.imagejskzp.image= image;
                vm.imagejskzp.imageName = imageName;
            }
        }
    }
});

// 确认注册区域
var submitArea = new Vue({
    el:'#submitArea',
    data:{
        applyMark:true//是否选中支付协议
    },
    computed:{
        enableSubmitBtn:function () {

            return {
                disableSubmit:!quaryMerchantInfo()
            }

        }
    }

});

//点击查询银行按钮
bankCardInfo.search = function () {
    qieryBranckBankList();
};



//点击提交申请
function submit() {
    if (quaryMerchantInfo()){
        createMerchantInfo(getSubmitParams());
    }
};


// 处理提交资料返回结果
function createMerchantInfoCallBack(data) {
    if(data.result != undefined){
        var result = Number(data.result);
        if (result == 1){
            $('#resultModal').modal('show');
            $('#merchantNo').text(data.merchantNo);
        }else {
            showTick(data.msg);
        }
    }

};
// 处理查询银行返回结果
function banklistxCallback(data) {
    if(data.rows != undefined){
        bankCardInfo.branchBankList = data.rows;
    }
}




// 检查待提交参数完整性
function quaryMerchantInfo() {

    if(merchantInfo.fullName.length == 0){
        return false;
    }
    if(merchantInfo.shortName.length == 0){
        return false;
    }
    if(merchantInfo.servicePhone.length == 0){
        return false;
    }
    if(merchantInfo.businessLicense.length == 0){
        return false;
    }
    if(merchantInfo.businessLicenseType.length == 0){
        return false;
    }

    if(contactInfo.contactName.length == 0){
        return false;
    }
    if(contactInfo.contactPhone.length == 0){
        return false;
    }
    if(contactInfo.contactPersonType.length == 0){
        return false;
    }
    if(contactInfo.contactIdCard.length == 0){
        return false;
    }
    if(contactInfo.BIZCATEGORY.length == 0 || contactInfo.BIZCATEGORY === "0"){
        return false;
    }

    if(merchantInfo.province.length == 0 || merchantInfo.province === "0"){
        return false;
    }
    if(merchantInfo.city.length == 0 || merchantInfo.city === "0"){
        return false;
    }if(merchantInfo.district.length == 0 || merchantInfo.district === "0"){
        return false;
    }if(merchantInfo.address.length == 0){
        return false;
    }

    if(bankCardInfo.bankAccountNo.length == 0){
        return false;
    }
    if(bankCardInfo.bankAccountName.length == 0){
        return false;
    }
    if(bankCardInfo.idCard.length == 0){
        return false;
    }
    if(bankCardInfo.bankAccountType.length == 0){
        return false;
    }
    if(bankCardInfo.branchBank.BANK_CHANNEL_NO == undefined){
        return false;
    }

    if(uploadImgs.imageyyzz.imageName.length == 0){
        return false;
    }
    if(uploadImgs.imagefrsfza.imageName.length == 0){
        return false;
    }
    if(uploadImgs.imagefrsfzb.imageName.length == 0){
        return false;
    }

    if(uploadImgs.imagedpzpmt.imageName.length == 0){
        return false;
    }
    if(uploadImgs.imagedpzpsyt.imageName.length == 0){
        return false;
    }
    if(uploadImgs.imagedpzpjycs.imageName.length == 0){
        return false;
    }


    if (uploadImgs.showJskzp){//对公:不需要结算卡信息 对私:需要结算卡信息
        if(uploadImgs.imagejskzp.imageName.length == 0){
            return false;
        }
        if(uploadImgs.imagejskcyrsfza.imageName.length == 0){
            return false;
        }
        if(uploadImgs.imagejskcyrsfzb.imageName.length == 0){
            return false;
        }
    }
    if (uploadImgs.showZZJGDMZZP){//商户信息证件类型为营业执照多证合一不需要组织机构代码证
        if(uploadImgs.imagezzjg.imageName.length == 0){
            return false;
        }
    }
    return true;
}

// 检查查询银行的参数的完整性
function qieryBranckBankList() {
    var validResult = true;
    if(bankCardInfo.province == "0"){
        bankCardInfo.provinceErrorTick = true;
        validResult = false;
    }
    if(bankCardInfo.city == "0"){
        bankCardInfo.cityErrorTick = true;
        validResult = false;
    }
    if(bankCardInfo.bankCode == "0"){
        bankCardInfo.bankCodeErrorTick = true;
        validResult = false;
    }
    if(validResult){

        queryBanklist();
    }
}


// 获取提交审核的参数
function getSubmitParams() {
    //商户信息
    var MerchantInfo = {
        fullName:merchantInfo.fullName,
        shortName:merchantInfo.shortName,
        servicePhone:merchantInfo.servicePhone,
        businessLicense:merchantInfo.businessLicense,
        businessLicenseType:merchantInfo.businessLicenseType,
    }

    // MerchantInfo.remitType = bankCardInfo.remitType;
    //联系人信息
    var ContactInfo = {
        contactName : contactInfo.contactName,
        contactPhone:contactInfo.contactPhone,
        contactPersonType:contactInfo.contactPersonType,
        contactIdCard:contactInfo.contactIdCard,
    }
    if (contactInfo.contactEmail.length){
        ContactInfo.contactEmail = contactInfo.contactEmail;
    }
    //商户地址信息
    var AddressInfo = {
        province:merchantInfo.province,
        city:merchantInfo.city,
        district:merchantInfo.district,
        address:merchantInfo.address
    }

    // 银行卡信息
    var BankCardInfo = {
        bankAccountNo:bankCardInfo.bankAccountNo,
        bankAccountName:bankCardInfo.bankAccountName,
        idCard:bankCardInfo.idCard,
        bankAccountType:bankCardInfo.bankAccountType,
        bankAccountLineNo:bankCardInfo.branchBank.BANK_CHANNEL_NO,
        bankAccountAddress:bankCardInfo.branchBank.BANK_NAME
    }

    MerchantInfo.contactInfo = ContactInfo;
    MerchantInfo.addressInfo = AddressInfo;
    MerchantInfo.bankCardInfo = BankCardInfo;

    //营业执照

    //经营类目
    // MerchantInfo.BIZCATEGORY = contactInfo.BIZCATEGORY;
    var data = {
        action: "createMerchantInfo",
        merchantInfo:JSON.stringify(MerchantInfo),
        accid:GetQueryString("accid"),
        bizCateGory:contactInfo.BIZCATEGORY,
        remitType:bankCardInfo.remitType,
        imageyyzz:uploadImgs.imageyyzz.imageName,
        imagefrsfza:uploadImgs.imagefrsfza.imageName,
        imagefrsfzb:uploadImgs.imagefrsfzb.imageName,
        imagedpzpmt:uploadImgs.imagedpzpmt.imageName,
        imagedpzpsyt:uploadImgs.imagedpzpsyt.imageName,
        imagedpzpjycs:uploadImgs.imagedpzpjycs.imageName
    }
    if(uploadImgs.showZZJGDMZZP){
        data.imagezzjg = uploadImgs.imagezzjg.imageName;
    }
    if(uploadImgs.showJskzp){
        data.imagejskzp = uploadImgs.imagejskzp.imageName;
        data.imagejskcyrsfza = uploadImgs.imagejskcyra.imageName;
        data.imagejskcyrsfzb = uploadImgs.imagejskcyrb.imageName;
    }
    return data;
}

//查询支行列表
function queryBanklist() {

    var findbox = bankCardInfo.quaryBranchBank;
    var type_code = bankCardInfo.bankCode;
    var city = bankCardInfo.city;
    var province = bankCardInfo.province;

    if (type_code.length && city.length && province.length){
        var data = {"flyang":"20150107","page":-1,"fieldlist":"*","city":city,"province":province,"type_code":type_code};
        if(findbox.length){
            data.findbox = findbox;
        }

        var sign = {"timestamp":new Date().Format("yyMMddhhmmss")};
        var params = {
            "data":data,
            "sign":sign
        }
        pubRequestDataWithActionAndParamsAndCallback("banklistx",params,banklistxCallback);
    }
}
/**
 * pub 单元的请求
 * @param action:拼接URL的action
 * @param params:参数
 * @param callBack:回调函数
 */
function pubRequestDataWithActionAndParamsAndCallback(action, params, callBack){
    var url = BasePubURL + action;
    showLoading();
    $.ajax({
        url: url,
        type:"POST",
        data: {
            data:JSON.stringify(params.data),
            sign:JSON.stringify(params.sign)
        },
        success: function(data){
            try{
                var obj = eval('('+data+')');
           /*     console.log("网络请求测试: \nURL-->>" + url + "\n参数-->>" + JSON.stringify(params) + "\n返回值-->>" + JSON.stringify(data))*/

                if(obj.result != undefined){
                    var result = Number(obj.result);
                    if (result == 0){
                        showTick(obj.msg);
                    }else {
                        callBack(obj);
                    }
                }else if(obj.total != undefined){
                    callBack(obj);
                }
            }catch (ex){
                console.log(ex.message);
            }
        },
        complete:function () {
            endLoading();
        }

    });
}

/**
 * 提交资料的请求
 * @param submitData:所有的参数
 */
function createMerchantInfo(submitData) {
    showLoading();
    $.ajax({
        url: "/fypay/fypayapi",
        type:"POST",
        dataType: "json",
        data: submitData,
        success: function(data){
            try{
                createMerchantInfoCallBack(data);
            }catch (ex){
                console.log(ex.message);
            }
        },
        complete:function (e) {
            endLoading();
        }

    });
}

/**
 * 提交资料的图片
 * @param imageBase64Str:所有的参数
 */
function uploadImage(image,index) {
    // var imageArr = image.split(",");
    // var imageBase64Str = uploadImgs.imageScale(imageArr[1]);
    
    showLoading();
    compress(image,function(imageBase64Str){
        if(imageBase64Str==undefined){
        	showTick("图片上传失败，请重新上传！");
        	return;
        }
        var imageArr = imageBase64Str.split(",");
        imageBase64Str = imageArr[1];
    	$.ajax({
        url: "https://jerp.skydispark.com:62221/skyderp/pub?action=uploadimagex",
        type:"POST",
        dataType: "json",
        data: {
            pict:JSON.stringify({image:encodeURI(imageBase64Str)}),
            data:JSON.stringify({flyang:"20150107"}),
            sign:JSON.stringify({timestamp:new Date().Format("yyMMddhhmmss"),maccid:"0",userid:"0",signature:""})
        },
        success: function(data){
            try{
                window.console.log(JSON.stringify(data));
                if (data.result == 1){
                    uploadImgs.setImageUrlName(image,index,data.msg);
                    // showTick("图片保存成功！");
                }else {
                    showTick(data.msg);
                }
            }catch (ex){
                console.log(ex.message);
            }
        },
        complete:function (e) {
            endLoading();
        }

    });
    });
}


/**——————————————————————————————————————————————————————————————一些工具——————————————————————————————————————————————————————————————————————*/

// 展示提示
function showTick(msg) {
    // 交互提示内容
    var tickContent = $("#tickContent");
    tickContent.text(msg);

    // toast
    var $toast = $('#toast');
    if ($toast.css('display') != 'none') return;

    $toast.fadeIn(100);
    setTimeout(function () {
        $toast.fadeOut(100);
    }, 2000);
}
// 展示加载中
function showLoading() {
    // loading
    var $loadingToast = $('#loadingToast');
    if ($loadingToast.css('display') != 'none') return;
    $loadingToast.fadeIn(100);
    setTimeout(function () {
        $loadingToast.fadeOut(100);
    }, 2000);
}
// 隐藏加载中
function endLoading() {
    var $loadingToast = $('#loadingToast');
    if ($loadingToast.css('display') != 'block') return;
    $loadingToast.fadeOut(100);
}
// 获取地址栏参数的方法
function GetQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}
/**
 * 时间格式化
 */
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
                : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt
}

/**
 * 获取城市列表
 * @param type = 1:选择开户行的城市; = 2:选择注册地址的城市
 * @param newProvice 对象
 * @returns {Array}
 */
function getCitiesWithType(newProvice){
    var provinceName = newProvice;
    var cities = [];

    for(var i = 0; i < areasForSubmit.length ; i++){
        if(provinceName == areasForSubmit[i].provinceName && !containsValue(cities,"cityName",areasForSubmit[i].cityName)){
            cities.push(areasForSubmit[i]);
        }
    }

    return cities;
};
/**
 * 获取选择注册地址区、镇列表
 * @param newCity 传入的城市
 * @returns {Array}
 */
function getCounties(newCity){
    var cityName = newCity;
    var counties = [];
    for(var i = 0; i < areasForSubmit.length ; i++){
        if(cityName == areasForSubmit[i].cityName && !containsValue(counties,"countyName",areasForSubmit[i].countyName)){
            counties.push(areasForSubmit[i]);
        }
    }
    return counties;
};

//获取经营类型A的二级项目；
function get2Cate(A){
    var bArr = [];
    var b = "";
    for(var i in businessType){
        var row = businessType[i];
        var _b = row.b;
        if(row.a==A){
            if(b!=_b){
                bArr.push(_b);
                b=_b;
            }
        }
    }
    return bArr;
}
//获取经营类型B的三级项目；
function get3Cate(A,B){
    var cArr = [];
    for(var i in businessType){
        var row = businessType[i];
        var b = row.b;
        var c = row.c;
        if(row.a==A){
            if(B=b){
                cArr.push({
                    name: c,
                    value: row.code
                });
            }else if(cArr.length>0) break;

        }
    }
    return cArr;
}
// 获取时间戳
function getTimeSnap() {

    var date = new Date();

    return DateToStr(date);;

}

//将日期转化字符串(yyyy-MM-dd)
function DateToStr(date){
    var year = date.getFullYear();
    var month =(date.getMonth() + 1).toString();
    var day = (date.getDate()).toString();
    var h = date.getHours();

    var m = date.getMinutes();

    var s = date.getSeconds();

    if (month.length == 1) {
        month = "0" + month;
    }
    if (day.length == 1) {
        day = "0" + day;
    }
    if (h.length == 1) {
        h = "0" + h;
    }
    if (m.length == 1) {
        m = "0" + m;
    }
    if (s.length == 1) {
        s = "0" + s;
    }
    dateTime = year +""+ month +""+  day + "" + h + "" + m + "" + s;
    return dateTime;
}
/**
 * 验证手机号
 * @param phone 传入的手机号
 * @returns {Boolen}
 */
function validPhone(phone) {
    if(!(/^((13[0-9])|14[57]|(15[^4,\D])|17[013678]|(18[0-9]))\d{8}$/.test(phone))){
        return false;
    }else {
        return true;
    }
}
// 验证身份证号
function validIDCard(IDCard) {
    if(!(/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/).test(IDCard)){
        return false;
    }else {
        return true;
    }
}

/**
 * 判断数组中是否包含key对应的值为value的对象
 * @param arr
 * @param key
 * @param value
 * @returns {Boolean}
 */
function containsValue(arr, key, value) {
    if (isArray(arr)) {
        for (var i = 0; i < arr.length; i++) {
            if (arr[i][key] === value) {
                return true;
            }
        }
    }
    return false;
}
/**
 * 判断对象是否是数组
 * @param o 对象
 * @returns {Boolean}
 */
function isArray(o){
    return Object.prototype.toString.call(o)=='[object Array]';
}


function compress (objImg,resultCallback) {
    // window.console.log("压缩前"+(objImg.length/1000));
    //--执行resize。
    var _ir=ImageResizer({
        resizeMode:"auto"
        ,dataSource:objImg
        ,dataSourceType:"base64"
        ,maxWidth:800 //允许的最大宽度
        ,maxHeight:600 //允许的最大高度。
        ,onTmpImgGenerate:function(img){

        }
        ,success:function(resizeImgBase64,canvas){
            // window.console.log("压缩后"+(resizeImgBase64.length/1000));
        	resultCallback(resizeImgBase64);
        }
        ,debug:true
    });

}


/**——————————————————————————————————————————————————————————————一些工具——————————————————————————————————————————————————————————————————————*/

/**
 * 这是基于html5的前端图片工具，压缩工具。
 */
var ImageResizer=function(opts){
    var settings={
        resizeMode:"auto"//压缩模式，总共有三种  auto,width,height auto表示自动根据最大的宽度及高度等比压缩，width表示只根据宽度来判断是否需要等比例压缩，height类似。
        ,dataSource:"" //数据源。数据源是指需要压缩的数据源，有三种类型，image图片元素，base64字符串，canvas对象，还有选择文件时候的file对象。。。
        ,dataSourceType:"image" //image  base64 canvas
        ,maxWidth:150 //允许的最大宽度
        ,maxHeight:200 //允许的最大高度。
        ,onTmpImgGenerate:function(img){} //当中间图片生成时候的执行方法。。这个时候请不要乱修改这图片，否则会打乱压缩后的结果。
        ,success:function(resizeImgBase64,canvas){

        }//压缩成功后图片的base64字符串数据。
        ,debug:false //是否开启调试模式。

    };
    var appData={};
    $.extend(settings,opts);

    var _debug=function(str,styles){
        if(settings.debug==true){
            if(styles){
                console.log(str,styles);
            }
            else{
                console.log(str);
            }
        }
    };
    var innerTools={
        getBase4FromImgFile:function(file,callBack){
            var reader = new FileReader();
            reader.onload = function(e) {
                var base64Img= e.target.result;
                //var $img = $('<img>').attr("src", e.target.result)
                //$('#preview').empty().append($img)
                if(callBack){
                    callBack(base64Img);
                }
            };
            reader.readAsDataURL(file);
        }

        //--处理数据源。。。。将所有数据源都处理成为图片图片对象，方便处理。
        ,getImgFromDataSource:function(datasource,dataSourceType,callback){
            var _me=this;
            var img1=new Image();
            if(dataSourceType=="img"||dataSourceType=="image"){
                img1.src=$(datasource).attr("src");
                if(callback){
                    callback(img1);
                }
            }
            else if(dataSourceType=="base64"){
                img1.src=datasource;
                if(callback){
                    callback(img1);
                }            }
            else if(dataSourceType=="canvas"){
                img1.src = datasource.toDataURL("image/jpeg");
                if(callback){
                    callback(img1);
                }
            }
            else if(dataSourceType=="file"){
                _me.getBase4FromImgFile(function(base64str){
                    img1.src=base64str;
                    if(callback){
                        callback(img1);
                    }
                });
            }

        }
        //计算图片的需要压缩的尺寸。当然，压缩模式，压缩限制直接从setting里面取出来。
        ,getResizeSizeFromImg:function(img){
            var _img_info={
                w:$(img)[0].naturalWidth,
                h:$(img)[0].naturalHeight
            };
            // console.log("真实尺寸：");
            // console.log(_img_info);
            var _resize_info={
                w:0
                ,h:0
            };
            if(_img_info.w<=settings.maxWidth&&_img_info.h<=settings.maxHeight){
                return _img_info;
            }
            if(settings.resizeMode=="auto"){
                var _percent_scale=parseFloat(_img_info.w/_img_info.h);
                var _size1={
                    w:0
                    ,h:0
                };
                var _size_by_mw={
                    w:settings.maxWidth
                    ,h:parseInt(settings.maxWidth/_percent_scale)
                };
                var _size_by_mh={
                    w:parseInt(settings.maxHeight*_percent_scale)
                    ,h:settings.maxHeight
                };
                if(_size_by_mw.h<=settings.maxHeight){
                    return _size_by_mw;
                }
                if(_size_by_mh.w<=settings.maxWidth){
                    return _size_by_mh;
                }

                return {
                    w:settings.maxWidth
                    ,h:settings.maxHeight
                };

            }
            if(settings.resizeMode=="width"){
                if(_img_info.w<=settings.maxWidth){
                    return _img_info;
                }
                var _size_by_mw={
                    w:settings.maxWidth
                    ,h:parseInt(settings.maxWidth/_percent_scale)
                };
                return _size_by_mw;
            }

            if(settings.resizeMode=="height"){
                if(_img_info.h<=settings.maxHeight){

                    return _img_info;
                }
                var _size_by_mh={
                    w:parseInt(settings.maxHeight*_percent_scale)
                    ,h:settings.maxHeight
                };
                return _size_by_mh;
            }

        }
        //--将相关图片对象画到canvas里面去。
        ,drawToCanvas:function(img,theW,theH,realW,realH,callback){
            var canvas = document.createElement("canvas");
            canvas.width=theW;
            canvas.height=theH;
            var ctx = canvas.getContext('2d');
            ctx.drawImage(img,
                0,//sourceX,
                0,//sourceY,
                realW,//sourceWidth,
                realH,//sourceHeight,
                0,//destX,
                0,//destY,
                theW,//destWidth,
                theH//destHeight
            );

            //--获取base64字符串及canvas对象传给success函数。
            var base64str=canvas.toDataURL("image/jpeg");
            if(callback){
                callback(base64str,canvas);
            }
        }
    };

    //--开始处理。
    (function(){
        innerTools.getImgFromDataSource(settings.dataSource,settings.dataSourceType,function(_tmp_img){
            setTimeout(function(){
                var __tmpImg=_tmp_img;
                settings.onTmpImgGenerate(_tmp_img);
                //--计算尺寸。
                var _limitSizeInfo=innerTools.getResizeSizeFromImg(__tmpImg);
                console.log(_limitSizeInfo);
                var _img_info={
                    w:$(__tmpImg)[0].naturalWidth,
                    h:$(__tmpImg)[0].naturalHeight
                };

                innerTools.drawToCanvas(__tmpImg,_limitSizeInfo.w,_limitSizeInfo.h,_img_info.w,_img_info.h,function(base64str,canvas){
                    settings.success(base64str,canvas);
                });
            },1000);


        });
    })();
    var returnObject={
    };
    return returnObject;
};