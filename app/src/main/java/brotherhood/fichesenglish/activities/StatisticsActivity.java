package brotherhood.fichesenglish.activities;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import brotherhood.fichesenglish.R;
import brotherhood.fichesenglish.utils.BaseActivity;

public class StatisticsActivity extends BaseActivity {
int i =0;
    @Override
    protected void customOnCreate() {
        setContentView(R.layout.activity_statistics);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Button bt_progress = (Button)findViewById(R.id.bt_progress);

        bt_progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setSecondaryProgress(21);
            }
        });

    }
}
