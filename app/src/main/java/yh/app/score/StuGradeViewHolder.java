package yh.app.score;

import android.widget.TextView;

public class StuGradeViewHolder
{
	private TextView course_name;
	private TextView course_grade;
	private TextView course_xf;

	public TextView getCourse_xf()
	{
		return course_xf;
	}

	public void setCourse_xf(TextView course_xf)
	{
		this.course_xf = course_xf;
	}

	public TextView getCourse_name()
	{
		return course_name;
	}

	public void setCourse_name(TextView course_name)
	{
		this.course_name = course_name;
	}

	public TextView getCourse_grade()
	{
		return course_grade;
	}

	public void setCourse_grade(TextView course_grade)
	{
		this.course_grade = course_grade;
	}

}
