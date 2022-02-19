const userAjaxUrl = "meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": true,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
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
    addAJAXFilterEventListener();
});

function addAJAXFilterEventListener(){
    $("#filterForm").submit(function (e) {
        e.preventDefault(); // avoid to execute the actual submit of the form.
        let form = $(this);

        $.ajax({
            type: "GET",
            url: ctx.ajaxUrl +"filter",
            data: form.serialize(),// serializes the form's elements.
            success: function (data) {
                ctx.datatableApi.clear().rows.add(data).draw();
            }
        });
    });
}