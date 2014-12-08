onPlay = 0;
curFrame = 1;

function drawRectangle(context, i, prefix) {
	var img = new Image();
	img.onload = function() {
		context.drawImage(img, 0, 0);
	}
	img.src = "assets/video/" + prefix + "." + i + ".jpg";
}

function animate(canvas, context, prefix) {
	if (onPlay == -1) {onPlay = 0; return;}
	if (curFrame >= 300 ){onPlay = 0; curFrame = 1; return;}
	drawRectangle(context, curFrame, prefix);
	curFrame++;

	// Render every 30 fps
	setTimeout(function () {
		animate(canvas, context, prefix);
	}, 1000/30);
}

$('.video').each(function(index) {
	if (this.getContext) { // `this` is an element each time
		var context = this.getContext('2d');
		var prefix = this.id.split(".")[0];

		drawRectangle(context, 1, prefix);
		this.addEventListener("click", function () {
			if (!onPlay) {onPlay = 1; animate(this, context, prefix);}
			else {onPlay = -1;}
		});
	}
});

$('.thumbnail').each(function(index) {
	if (this){
		this.addEventListener("click", function () {
			$(".feature").hide();
			$('#feature'+this.id).show();
		})
	}
});

$('.keyframe').each(function () {
	if (this){
		var t = this.id.split(".");
		this.addEventListener("click", function () {
			if (!onPlay) {
				curFrame = t[1];
				onPlay = 1;
				var canvas = document.getElementById(t[0]);
				var context = canvas.getContext('2d');
				animate(this, context, t[0]);}
			else {onPlay = -1;}
		})
	}
});

