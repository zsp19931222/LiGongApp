// package yh.app.quanzi;
//
// import java.util.ArrayList;
// import java.util.List;
//
// import android.content.Context;
// import android.view.View;
// import android.view.ViewGroup;
// import android.widget.BaseExpandableListAdapter;
// import android.widget.ExpandableListView;
// import yh.app.quanzitool.Add_quanzi_group_hand;
// import yh.app.quanzitool.InflaterView;
//
// public class initBMFZ
// {
// private ExpandableListView expandableListView;
// List<String> group;
// List<List<String>> child;
// private Context context;
//
// public initBMFZ(Context context, ExpandableListView expandableListView)
// {
// this.context = context;
// this.expandableListView = expandableListView;
// initializeData();
// initView();
// }
//
//
//
// private String[] array = new String[] { "英语系", "中文系", "办公室", "培训部", "药学系",
// "工程力学系", "电子信息工程系", "流通部" };
//
// private void initializeData()
// {
// group = new ArrayList<String>();
// child = new ArrayList<List<String>>();
// for (int i = 0; i < array.length; i++)
// {
// String[] array = new String[10];
// for (int j = 0; j < 10; j++)
// {
// array[j] = "测试好友" + i * 10 + j;
// }
// addInfo(this.array[i], array);
// array = null;
// }
// }
//
// private void addInfo(String string, String[] strings)
// {
// group.add(string);
// List<String> list = new ArrayList<String>();
// for (int i = 0; i < strings.length; i++)
// {
// list.add(strings[i]);
// }
// child.add(list);
// }
//
// private void initView()
// {
// expandableListView.setAdapter(new BaseExpandableListAdapter()
// {
// @Override
// public Object getChild(int groupPosition, int childPosition)
// {
// return child.get(groupPosition).get(childPosition);
// }
//
// @Override
// public long getChildId(int groupPosition, int childPosition)
// {
// return childPosition;
// }
//
// @Override
// public int getChildrenCount(int groupPosition)
// {
// return child.get(groupPosition).size();
// }
//
// @Override
// public View getChildView(int groupPosition, int childPosition, boolean
// isLastChild, View convertView, ViewGroup parent)
// {
// String string = child.get(groupPosition).get(childPosition);
// return getGenericView(string, 2);
// }
//
// @Override
// public Object getGroup(int groupPosition)
// {
// return group.get(groupPosition);
// }
//
// @Override
// public long getGroupId(int groupPosition)
// {
// return groupPosition;
// }
//
// @Override
// public int getGroupCount()
// {
// return group.size();
// }
//
// @Override
// public View getGroupView(int groupPosition, boolean isExpanded, View
// convertView, ViewGroup parent)
// {
// String string = group.get(groupPosition);
// return getGenericView(string, 1);
// }
//
// public View getGenericView(String s, int type)
// {
// if (type == 1)
// {
// return new Add_quanzi_group_hand(context).add(s);
// } else
// {
// return new InflaterView(context, s, "1").addLIXInflater();
// }
// }
//
// @Override
// public boolean hasStableIds()
// {
// return false;
// }
//
// @Override
// public boolean isChildSelectable(int groupPosition, int childPosition)
// {
// return true;
// }
// });
// }
// }
