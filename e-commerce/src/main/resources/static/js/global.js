  let host = 'http://localhost:8080/';

  function reloadCartTable(cart_id) {
    $("#cart_id").val(cart_id);
    $('#confirm').val(cart_id)
    let cartProductData = getProductFromCartById(cart_id);
    if (cartProductData.length == 0) {
      $("#dataorder").html(`
        <tr class="text-center">
            <td colspan="5" >
              No data entry
            </td>
        </tr>`);
      $("#tfoot").html("");
    } else {
      writeProductsToCartTable(cartProductData);
    }
  }

  function getCartById(cart_id) {
    let result;
    $.ajax({
      url: host + `orderservice/cart/${cart_id}`,
      type: "GET",
      contentType: "application/json",
      async: false,
      success: function (cartData) {
        result = cartData;
      },
    });
    return result;
  }

  function getProducts() {
    let result;
    $.ajax({
      url: host + "storeservice/products",
      type: "GET",
      async: false,
      contentType: "application/json",
      success: function (productData) {
        result = productData;
      },
    });
    return result;
  }
  function getProductById(id){
    let result;
    $.ajax({
      url: host + `storeservice/product/${id}`,
      type: "GET",
      async: false,
      contentType: "application/json",
      success: function (productData) {
        result = productData;
      },
    });
    return result;
  }
  function getProductByName(name) {
    let result;
    $.ajax({
      url: host + `storeservice/products/search/${name}`,
      type: "GET",
      async: false,
      contentType: "application/json",
      success: function (productData) {
        result = productData;
      },
    });
    return result;
  }
  
  function getLookupById(id) {
    let unit;
    $.ajax({
      url: host + `lookupservice/lookup/${id}`,
      type: "GET",
      async: false,
      contentType: "application/json",
      success: function (lookupData) {
        unit = lookupData.unit;
      },
    });
    return unit;
  }
  function registerModal(role) {
    console.log(role)
    $(".modal-title").html("Registering new user");
    $(".modal-body").html(`
    <div class="modal-body">
      <label for="email">Email: </label>
          <div class="form-group">
            <input id="email" type="text" placeholder="Email Address" class="form-control" >
          </div>
       <label for="firstname">Firstname: </label>
          <div class="form-group">
            <input id="firstname" type="text" placeholder="firstname" class="form-control" >
          </div>
      <label for="lastname">Lastname: </label>
          <div class="form-group">
            <input id="lastname" type="text" placeholder="lastname" class="form-control" >
          </div>
      <label for="age">Age: </label>
          <div class="form-group">
            <input id="age" type="number" placeholder="age" class="form-control">
          </div>
          <input id="role" type="hidden" placeholder="age" value="${role}" class="form-control">
      <label for="password">Password: </label>
          <div class="form-group">
            <input id="password" type="password" placeholder="Password" class="form-control" >
          </div>
      <label for="password">Confirm password: </label>
          <div class="form-group">
            <input id="confirmpassword" type="password" placeholder="confirmpassword" class="form-control" >
          </div>
     </div>
    `);
    $(".modal-footer").html(`
      <button type="reset" class="btn" onclick="resetForm()">
        <i class="bx bx-x d-block d-sm-none"></i>
        <span class="d-none d-sm-block">Reset</span>
      </button>
      <button
        type="button"
        value="Admin"
        class="btn btn-primary ms-1"
        data-bs-dismiss="modal"
        onclick="addUser(this.value)"
      >
        <i class="bx bx-check d-block d-sm-none"></i>
        <span class="d-none d-sm-block">Save</span>
      </button>
    `);
  }
  function resetForm(){
    $("#email").val("");
    $("#password").val("");
    $("#confirmpassword").val("");
    $('#firstname').val("");
    $('#lastname').val("");
    $('#age').val("");
  }
  function addUser() {
    const email = $("#email").val();
    const password = $("#password").val();
    const confirmPassword = $("#confirmpassword").val();
    const firstname = $('#firstname').val();
    const lastname = $('#lastname').val();
    const age = $('#age').val();
    const role = $('#role').val();
  
    if(password == confirmPassword){
      let data = {
        email: email,
        firstName: firstname,
        lastName: lastname,
        age:age,
        status : "Active",
        role: role,
        password: password,
  
      };
      $.ajax({
        url: host+"userservice/users",
        type: "POST",
        dataType: "json",
        data: JSON.stringify(data),
        contentType: "application/json",
        success: function (userData) {
          Swal.fire({
            position: "top-end",
            icon: "success",
            title: "User added",
            showConfirmButton: false,
            timer: 1500,
          }).then((result) => {
            location.reload();
          });

        },
        error: function (err) {
          console.log(err.responseText);
        },
      });
    }else{
      Swal.fire({
        position: "top-end",
        icon: "error",
        title: "confirm password is'n match with password",
        showConfirmButton: false,
        timer: 1500,
      });
    }
   
  }

  const formatter = new Intl.NumberFormat('id-ID',{
      style : 'currency',
      currency: 'IDR'
  })

  function closeModal() {
    $("#default").modal('hide')
    $(".modal-backdrop").removeClass("modal-backdrop")
  }

  function toastSuccess(message) {
    Toastify({
      text: message,
      duration: 3000,
      close: true,
      backgroundColor: "#4fbe87",
    }).showToast();
  }
  function toastFailed(message) {
    Toastify({
      text: message,
      duration: 3000,
      close: true,
      backgroundColor: "#F47174",
    }).showToast();
  }

  function initDataTable(){
    $("#table1").DataTable();
  }