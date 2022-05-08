package tw.edu.pu.csim.tcyang.tcyang2022shootinggame

import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import kotlinx.coroutines.*
@GlideModule
public final class MyAppGlideModule : AppGlideModule()

class MainActivity : AppCompatActivity() {

    lateinit var img : ImageView
    lateinit var game : Game
    var flag:Boolean = false
    lateinit var job : Job
    lateinit var imgAuthor : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        img = findViewById(R.id.img)
        game = findViewById(R.id.game)

        imgAuthor = findViewById(R.id.imgAuthor)
        GlideApp.with(this)
            //.load(R.drawable.earth)
            .load(R.drawable.tcyang)
            .circleCrop()
            .override(800, 600)
            .into(imgAuthor)

        img.setOnClickListener({
            if (flag){
                flag = false
                img.setImageResource(R.drawable.start)
                job.cancel()
            }
            else{
                flag = true
                img.setImageResource(R.drawable.stop)
                job = GlobalScope.launch(Dispatchers.Main) {
                    while(flag) {
                        delay(10)
                        var canvas: Canvas = game.surfaceHolder.lockCanvas()
                            game.drawSomething(canvas)
                        game.surfaceHolder.unlockCanvasAndPost(canvas)
                    }
                }
            }

        })
    }
}