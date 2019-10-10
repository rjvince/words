$(document).ready(function () {
    var $wordcoll = $("#wordcoll");

    $.get('word/today', function (s) {
       $('#word-header').text('The word of the day is:');
        $wordcoll.append($("<li></li>").addClass('collection-item').text(s));
    });

    $('button#today-btn').on('click', function () {
        $.get('word/today', function (s) {
            $('#wordcoll li.collection-item').remove();
            $('#word-header').text('The word of the day is:');
            $wordcoll.append($("<li></li>").addClass('collection-item').text(s));
        });
    });
    $('button#random-btn').on('click', function () {
        $.get('word/random', function (s) {
            $('#wordcoll li.collection-item').remove();
            $('#word-header').text('Your random word is:');
            $wordcoll.append($("<li></li>").addClass('collection-item').text(s));
        });
    });
    $('button#multi-btn').on('click', function () {
        $.get('word/random?many=5', function (s) {
            var words = s.split(", ");
            $('#wordcoll li.collection-item').remove();
            $('#word-header').text('Your five random words are: ');
            words.forEach(function(item) {
               $wordcoll.append($("<li></li>").addClass('collection-item').text(item));
            });
        });
    });
});