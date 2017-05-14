/**
 * Created by STANISLAV on 13.05.2017.
 */
$(document).ready(function () {
    var $form = $("#form"),
        $resultMessage = $("#result_message");
    var $submitButton = $form.find('button[type="submit"]');

    var spinnerHtml = '<i class="fa fa-circle-o-notch fa-spin fa-fw"></i>' +
        '<span class="sr-only">Подождите...</span>';

    var allowedNumKeyCodes = [
        8, // delete
        9, // tab
        13, // escape
        35, // end
        36, // home
        37, // left
        38, // up
        39, // right
        40, // down
        46, // backspace
        48, 49, 50, 51, 52, 53, 54, 55, 56, 57,  // 0-9
        96, 97, 98, 99, 100, 101, 102, 103, 104, 105 // num1-num9
    ];

    var allowedBinKeyCodes = [
        8, // delete
        9, // tab
        13, // escape
        35, // end
        36, // home
        37, // left
        38, // up
        39, // right
        40, // down
        46, // backspace
        48, // 0
        49, // 1
        96, // num0
        97 // num1
    ];

    $form.submit(function (event) {
        event.preventDefault();
        $resultMessage.addClass("hidden");
        var submitButtonText = $submitButton.text();
        $submitButton.html(spinnerHtml);
        $submitButton.attr("disabled", "disabled");
        var formObject = $(this).serializeObject();
        var formData = new FormData();
        formData.append("trainingSampleJson", JSON.stringify(formObject.trainingSample));
        formData.append("answersJson", JSON.stringify(formObject.answers));
        formData.append("testJson", JSON.stringify(formObject.test));
        $.ajax({
            type: 'POST',
            url: 'work',
            data: formData,
            processData: false,
            contentType: false,
            async: false,
            cache: false,
            dataType: 'json',
            success: function (data) {
                if (data.status === "ok") {
                    $resultMessage.addClass("text-success");
                    $resultMessage.text("Входные данные классификатор отнес к классу: " + data.testClass);
                }
            },
            error: function () {
                $resultMessage.addClass("text-danger");
                $resultMessage.text("Произошла ошибка");
            },
            complete: function () {
                $resultMessage.removeClass("hidden");
                $submitButton.text(submitButtonText);
                $submitButton.removeAttr("disabled", "disabled");
            }
        });
    });

    $(document).on('keyup keypress', 'form input[type=text]', function (e) {
        var keyCode = e.keyCode || e.which;
        if (keyCode === 13) {
            e.preventDefault();
            return false;
        }
    });

    $(".input-numbers-only").keydown(function (e) {
        if (!isAllowedEvent(e, allowedNumKeyCodes)) {
            e.preventDefault();
        }
    });

    $(".input-binary-only").keydown(function (e) {
        if (!isAllowedEvent(e, allowedBinKeyCodes)) {
            e.preventDefault();
        }
    });

    $(".select-on-click").click(function () {
        $(this).select();
    });

    $(document.body).on('keydown', '.bootstrap-tagsinput > input', function (e) {
        if (!isAllowedEvent(e, allowedNumKeyCodes)) {
            e.preventDefault();
        }
    });

    function isAllowedEvent(event, allowedKeyCodesArray) {
        return !(event.shiftKey === true || !($.inArray(event.keyCode, allowedKeyCodesArray) !== -1 ||
        // Ctrl+A, Command+A
        (event.keyCode === 65 && (event.ctrlKey === true || event.metaKey === true)) ||
        // Ctrl+C, Command+C
        (event.keyCode === 67 && (event.ctrlKey === true || event.metaKey === true)) ||
        // Ctrl+V, Command+V
        (event.keyCode === 86 && (event.ctrlKey === true || event.metaKey === true))));
    }

    $('input.tagsinput').tagsinput({
        maxChars: 9,
        trimValue: true
    });
});
