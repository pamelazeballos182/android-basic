package com.example.zeballos.todoapplication;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import Controllers.TaskController;
import dataBase.TaskContract;


public class MainActivity extends ListActivity {
    private ListAdapter listAdapter;
    private TaskController taskController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskController = new TaskController();
        updateUI();
    }


    public void addAction(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add a task");
        builder.setMessage("What do you want to do?");
        final EditText inputField = new EditText(this);
        builder.setView(inputField);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String task = inputField.getText().toString();
                taskController.insertTask(task, MainActivity.this);
                updateUI();
            }
        });

        builder.setNegativeButton("Cancel",null);

        builder.create().show();
    }


    private void updateUI() {
        listAdapter = new SimpleCursorAdapter(
                this,
                R.layout.layout_task,
                taskController.updateTask(MainActivity.this),
                new String[]{TaskContract.Columns.TASK},
                new int[]{R.id.taskTextView},
                0
        );

        this.setListAdapter(listAdapter);
    }

    public void onDoneButtonClick(View view) {
        View v = (View) view.getParent();
        TextView taskTextView = (TextView) v.findViewById(R.id.taskTextView);
        String task = taskTextView.getText().toString();

        taskController.deleteTask(task, MainActivity.this);
        updateUI();
    }

}
