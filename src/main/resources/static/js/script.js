// setInterval(function () {
//
//     let response = "";
//     let row = "";
//     let id = $("#id").val();
//
//     $.ajax({
//         url: "/api/orders/" + id,
//         contentType: "application/json",
//         type: "GET",
//         success: function (response) {
//             let jsonObj = $.parseJSON(JSON.stringify(response));
//             for (i = 0; i < jsonObj.length; i++) {
//                 row += "<tr>" +
//                     "<td>" + jsonObj[i].orderItemEmployee + "</td>" +
//                     "<td>" + jsonObj[i].orderItemDescription + "</td>" +
//                     "<td>" + jsonObj[i].orderItemPrice + "</td>" +
//                     "</tr>";
//             }
//             $("#orderitemdata").html(row);
//         }
//     });
//
// }, 2000);