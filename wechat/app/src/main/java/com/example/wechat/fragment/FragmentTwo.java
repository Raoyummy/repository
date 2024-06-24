package com.example.wechat.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wechat.R;
import com.example.wechat.dbutil.UserDbHelper;
import com.example.wechat.untity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FragmentTwo extends Fragment implements View.OnClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    private UserDbHelper dbHelper;
    private ImageView adduser;
    private ListView userList;
    private int[] pic = new int[]{
            R.drawable.c1,
            R.drawable.c2,
            R.drawable.c3,
            R.drawable.c5,
    };

    // 定义一个map集合存放数据
    private List<Map<String, Object>> list = new ArrayList<>();
    private SimpleAdapter simpleAdapter; // 适配器

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_two, container, false);

        adduser = v.findViewById(R.id.adduser);
        userList = v.findViewById(R.id.userlist);
        adduser.setOnClickListener(this);
        // 绑定长按事件
        userList.setOnItemLongClickListener(this);
        // 绑定单击事件
        userList.setOnItemClickListener(this);
        dbHelper = new UserDbHelper(getActivity());
        select();
        return v;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.adduser) {
            showCustomizeDialog();
        }
    }

    // 控制控件是否可以编辑
    public void checkEdit(EditText edit[], boolean flag) {
        int color = Color.rgb(204, 204, 204);
        if (flag) {
            color = Color.BLACK;
        }
        for (int i = 0; i < edit.length; i++) {
            edit[i].setEnabled(flag);
            edit[i].setTextColor(color);
        }
    }

    // 添加用户对话框
    private void showCustomizeDialog() {
        AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(getActivity());
        final View dialogView = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_adduser, null);
        customizeDialog.setTitle("添加朋友");
        customizeDialog.setIcon(R.drawable.a_b);
        customizeDialog.setView(dialogView);
        customizeDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 获取EditView中的输入内容
                        EditText username =
                                (EditText) dialogView.findViewById(R.id.username);
                        EditText phone =
                                (EditText) dialogView.findViewById(R.id.phone);
                        EditText email =
                                (EditText) dialogView.findViewById(R.id.email);
                        User user = new User(0, username.getText().toString(), phone.getText().toString(), email.getText().toString());
                        boolean b2 = dbHelper.insertData(user);
                        Toast.makeText(getActivity(), b2 ? "恭喜，添加成功!" : "添加失败", Toast.LENGTH_SHORT).show();
                        select();
                    }
                });
        customizeDialog.show();
    }

    // 用户信息及修改对话框
    private void showUserInfoAndUpdateDialog(String name) {
        AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(getActivity());
        final View dialogView = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_infouser, null);
        customizeDialog.setTitle(name + "的信息");
        customizeDialog.setIcon(R.drawable.b2);
        customizeDialog.setView(dialogView);

        // 获取EditView中的输入内容
        EditText idEdit =
                (EditText) dialogView.findViewById(R.id.id);
        EditText usernameEdit =
                (EditText) dialogView.findViewById(R.id.username);
        EditText phoneEdit =
                (EditText) dialogView.findViewById(R.id.phone);
        EditText emailEdit =
                (EditText) dialogView.findViewById(R.id.email);
        TextView editText =
                (TextView) dialogView.findViewById(R.id.editText);

        // 给编辑修改文字点击事件
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取编辑文字的值
                String text = editText.getText().toString();
                if (text.equals("编辑修改")) {
                    // 设置允许修改数据
                    checkEdit(new EditText[]{usernameEdit, phoneEdit, emailEdit}, true);
                    editText.setText("禁止修改");
                }
                if (text.equals("禁止修改")) {
                    // 设置不允许修改数据
                    checkEdit(new EditText[]{usernameEdit, phoneEdit, emailEdit}, false);
                    editText.setText("编辑修改");
                }
            }
        });

        Cursor cursor = dbHelper.getUserByUsername(name);
        if (cursor.moveToNext()) {
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name2 = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
            idEdit.setText(id);
            usernameEdit.setText(name2);
            phoneEdit.setText(phone);
            emailEdit.setText(email);

            checkEdit(new EditText[]{idEdit, usernameEdit, phoneEdit, emailEdit}, false);
        }
        cursor.close();

        customizeDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        User user = new User(Integer.parseInt(idEdit.getText().toString()), usernameEdit.getText().toString(), phoneEdit.getText().toString(), emailEdit.getText().toString());
                        boolean b2 = dbHelper.updateUserById(user);
                        Toast.makeText(getActivity(), b2 ? "修改成功!" : "修改失败", Toast.LENGTH_SHORT).show();
                        // 重新加载
                        select();
                    }
                });
        customizeDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });

        customizeDialog.show();
    }

    // 查询所有数据
    private void select() {
        list.clear();
        Cursor cursor = dbHelper.getData();
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<>();
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            map.put("pic", pic[new Random().nextInt(pic.length)]);
            map.put("name", name);
            list.add(map);
        }
        cursor.close();

        simpleAdapter = new SimpleAdapter(
                getActivity(),
                this.list,
                R.layout.user_list,
                new String[]{"pic", "name"},
                new int[]{R.id.pic, R.id.name}
        );

        userList.setAdapter(simpleAdapter);
    }

    // 长按事件
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap hashMap = (HashMap) parent.getItemAtPosition(position);
        String name = hashMap.get("name").toString();
        showDeleteUserDialog(name);
        return true;
    }

    // 确认删除的对话框
    private void showDeleteUserDialog(String name) {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getActivity());
        normalDialog.setIcon(R.drawable.ag2);
        normalDialog.setTitle("删除温馨提醒");
        normalDialog.setMessage("你确定要删除《" + name + "》这个用户嘛?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean b = dbHelper.deleteUserByName(name);
                        Toast.makeText(getActivity(), b ? "删除成功" : "删除失败", Toast.LENGTH_SHORT).show();
                        // 刷新listview
                        select();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }

    // 列表点击事件处理
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap hashMap = (HashMap) parent.getItemAtPosition(position);
        String name = hashMap.get("name").toString();
        showUserInfoAndUpdateDialog(name);
    }
}
