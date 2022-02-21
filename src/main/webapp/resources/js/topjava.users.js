const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    );

    addCheckBoxListener();
});

function addCheckBoxListener(){
    $('.checkbox').change(function(e){
        $.ajax({
            type: "get",
            url: "/topjava/rest/admin/users/enable",
            data: {
                'id':$(this).closest('tr').attr("id"),
                'enabled': $(this).prop('checked')
            },
            success: function () {
                updateTable();
            }
        })
    })
}