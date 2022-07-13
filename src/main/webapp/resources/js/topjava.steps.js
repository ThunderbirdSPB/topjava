const stepsAjaxUrl = "profile/steps/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: stepsAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: "GET",
            url: stepsAjaxUrl + "filter",
            data: $("#filter").serialize()
        }).done(updateTableByData);
    }
};

function clearFilter() {
    $("#filter")[0].reset();
    $.get(stepsAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable({
        "columns": [
            {
                "data": "date",
            },
            {
                "data": "numberOfSteps"
            },
            {
                "data": "burnedCalories"
            },
            {
                "render": renderEditBtn,
                "defaultContent": "",
                "orderable": false
            },
            {
                "render": renderDeleteBtn,
                "defaultContent": "",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            $(row).addClass("steps");
        }
    });
});