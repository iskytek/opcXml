function insertOpc() {
    $("#modalLabel").html("新增 Opc 配置");
    $("#pointName").val("");
    $("#tableName").val("");
    $("#idColumn").val("");
    $("#id").val("");
    $("#valueColumn").val("");
    $('#myModal').modal('show');
}

function deleteOpc() {
    if (selectTr != 0) {
        $.ajax({
            url: ctxPath + "/deleteOpc",
            data: {
                pointName: selectTr[0].children[0].innerHTML
            },
            success: function (data) {
                selectOpc();
                $("#tipText").html(data);
                $("#tipModal").modal('show');
            }
        });
    } else {
        $("#tipText").html("请至少选择一个 OPC 项 删除 ！");
        $("#tipModal").modal('show');
    }
}

function updateOpc() {
    if (selectTr != 0) {
        $("#modalLabel").html("编辑 Opc 配置");
        $("#pointName").val(selectTr[0].children[0].innerHTML);
        $("#tableName").val(selectTr[0].children[1].innerHTML);
        $("#idColumn").val(selectTr[0].children[2].innerHTML);
        $("#id").val(selectTr[0].children[3].innerHTML);
        $("#valueColumn").val(selectTr[0].children[4].innerHTML);
        $('#myModal').modal('show');
    } else {
        $("#tipText").html("请至少选择一个 OPC 项进行配置 ！");
        $("#tipModal").modal('show');
    }
}

function selectOpc() {
    $.ajax({
        url: ctxPath + "/selectOpc",
        dataType: "json",
        data: {
            text: $("#opcSearch").val()
        },
        success: function (data) {
            $("#opcList").html("");
            $.each(data, function (index, item) {
                $("#opcList").append("<tr>" +
                    "<td>" + item.pointName + "</td>" +
                    "<td>" + item.tableName + "</td>" +
                    "<td>" + item.idColumn + "</td>" +
                    "<td>" + item.id + "</td>" +
                    "<td>" + item.valueColumn + "</td>" +
                    "</tr>");
            });
            $("#opcTable tbody tr").click(function () {
                selectTr = $(this);
            });
        }
    });
}

function ajaxRequest() {
    var title = $("#modalLabel").text();
    if (title.indexOf("编辑") >= 0) {
        $.ajax({
            url: ctxPath + "/updateOpc",
            data: {
                old_pointName: selectTr[0].children[0].innerHTML,
                pointName: $("#pointName").val(),
                tableName: $("#tableName").val(),
                idColumn: $("#idColumn").val(),
                id: $("#id").val(),
                valueColumn: $("#valueColumn").val()
            },
            success: function (data) {
                $('#myModal').modal('hide');
                selectOpc();
                $("#tipText").html(data);
                $("#tipModal").modal('show');
            }
        });
    }
    if (title.indexOf("新增") >= 0) {
        $.ajax({
            url: ctxPath + "/insertOpc",
            data: {
                pointName: $("#pointName").val(),
                tableName: $("#tableName").val(),
                idColumn: $("#idColumn").val(),
                id: $("#id").val(),
                valueColumn: $("#valueColumn").val()
            },
            success: function (data) {
                $('#myModal').modal('hide');
                selectOpc();
                $("#tipText").html(data);
                $("#tipModal").modal('show');
            }
        });
    }
}