package yh.app.model;

import java.util.List;

/**
 * 应用模块
 * 
 * @author lft
 *
 */
public class ApplyModel
{


    /**
     * OTHER_APP : [{"NAME":"教学服务","LIST":[{"TYPE_NAME":"教学服务","FUNCTION_TYPE":"1","FUNCTION_ID":"20150120","UNCHANGE":"1","CLASS_NAME":"yh.app.function.FriendCycle","FUNCTION_NAME":"社区","TYPE_ID":"1","IOS_NOTICE":"segueForCircle","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/qz.png","PX":"4","INTEGRATE_KEY":["GbUdyvrrJ7pW8QP9luabi7JodC7cJtnJfQl5JU4G+vua2mTdCpXRYgNgDBLdgIGiNo9aN7sr+JTPt8qPSCe2LslsfrG5w5StXcM6KO2VyPkMNJXnYswF4WnLQRzmItUBuJJD5qaP2/bKH9bMhvpUN3NO0iaSu5HTUZsuclqyGzQ="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"教学服务","FUNCTION_TYPE":"2","FUNCTION_ID":"20150112","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"校园实景","TYPE_ID":"1","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/xysj.png","PX":"7","INTEGRATE_KEY":["nhjsbgeiR15DTidb/19oYxNwiegwmqWzF2h83T8ZSqJqNZmqcMDLJmFXfxEdgyy2gWtllicMG0l5vEkPubh1idl1VIMqlrYhHZ3IRYjvBoSHT60W24PL2zuXlF0gSB0deGMmmvFzd9fn++yEa0+Oqdy2xt9kMy7kXNmagm64/DA="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"教学服务","FUNCTION_TYPE":"1","FUNCTION_ID":"20150105","UNCHANGE":"0","CLASS_NAME":"yh.app.coursetable.TableDemoActivity","FUNCTION_NAME":"课表查询","TYPE_ID":"1","IOS_NOTICE":"segueForCourse","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/kbcx.png","PX":"10","INTEGRATE_KEY":["ikrEb2w/Sb7Aoxs89zZzRWxwby3gk9UqkDQqDJWKud47MYIP1MCTfuxKeApx7phjor4I4/Q3+nebMgfMAqmaoNr127ppcsGfnVoV2yH5G5699xdAqHcDpgndBjnMoiem85jGLWj24X7OLX1tzYayWr5Fx3mVZIJ/8mwTPIHorJA="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"教学服务","FUNCTION_TYPE":"2","FUNCTION_ID":"30000101","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"公文处理","TYPE_ID":"1","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/oabg.png","PX":"15","INTEGRATE_KEY":["RxZMtLhdd5EVz0N2pwK6sB/zweSxha9+pJtGLixOyyQnmkXkCdcNhcUXYb38F7TmLNoVPV7ayU1sDosIIuZ2r2ZC63m6+WS01HYu+b5Myb20rIM/WMgBmL5qca/c0ODB6cCIj81co/tFqRPTRDXMmM0j9o9EgFZH1tbAB/pECX4="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"教学服务","FUNCTION_TYPE":"2","FUNCTION_ID":"20150113","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"财务查询","TYPE_ID":"1","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/gzjf.png","PX":"19","INTEGRATE_KEY":["NJbP3A4HiKQWkAvAOz6cMbE1CMuc0ie2UuTIzVtFf2vj9mL0izKQls56AE4b8RkRhJPMUGpyIHMZ5h0KPpNFJdMagrMjd5NreizcT1hXK+7fd/Tr5J4A+Gq8sPN6RxltElQo9LFnPev8gjhPC4scSec08sUpUB5mXR6i01we+qc="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"教学服务","FUNCTION_TYPE":"2","FUNCTION_ID":"30000003","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"毕业审核","TYPE_ID":"1","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/bysh.png","PX":"40","INTEGRATE_KEY":["RxZMtLhdd5EVz0N2pwK6sB/zweSxha9+pJtGLixOyyQnmkXkCdcNhcUXYb38F7TmLNoVPV7ayU1sDosIIuZ2r2ZC63m6+WS01HYu+b5Myb20rIM/WMgBmL5qca/c0ODB6cCIj81co/tFqRPTRDXMmM0j9o9EgFZH1tbAB/pECX4="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"教学服务","FUNCTION_TYPE":"2","FUNCTION_ID":"30000006","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"监考查询","TYPE_ID":"1","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/jkcx.png","PX":"40","INTEGRATE_KEY":["nyndUaYs+kbs94yhVw2aUgsGWbVSTOh1/BM2W+f+Gw3sJLGpsCP5rceTHvkXxiM26QlyBeAl+HANzltKT9IOxeSnsU+KUX7TjiaMlRuvkWgY/5ENgKx2fBBYcOOam9WbXtU5HuGoqr0XKxvs4BZEEat6R+BaI6B3z85TQr1ilKg="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"教学服务","FUNCTION_TYPE":"2","FUNCTION_ID":"30000005","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"考试查询","TYPE_ID":"1","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/kscx.png","PX":"40","INTEGRATE_KEY":["nyndUaYs+kbs94yhVw2aUgsGWbVSTOh1/BM2W+f+Gw3sJLGpsCP5rceTHvkXxiM26QlyBeAl+HANzltKT9IOxeSnsU+KUX7TjiaMlRuvkWgY/5ENgKx2fBBYcOOam9WbXtU5HuGoqr0XKxvs4BZEEat6R+BaI6B3z85TQr1ilKg="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"}]},{"NAME":"学生服务","LIST":[{"TYPE_NAME":"学生服务","FUNCTION_TYPE":"2","FUNCTION_ID":"20150114","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"学校新闻","TYPE_ID":"2","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/xwpt.png","PX":"5","INTEGRATE_KEY":["qKHbDFUbHRUXQtgmUYyh0QDkskQ+F/G/oOF88iVimqxHXtPrtKlm1sTLqWipMb6EEoYIkKZlKO+xDNG7l3KExNp0SCKcVk9f9PsDImbFAeythtRhB6vv5qLBGP8XWXK83+bAODCDxjEDThvi1E3j7zf80Nk5phTh73dPO47VWyA="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"学生服务","FUNCTION_TYPE":"2","FUNCTION_ID":"20150111","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"图书借阅","TYPE_ID":"2","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/tscx.png","PX":"8","INTEGRATE_KEY":["ovJpzdRun2NDz/stPDzbPe0SzjKSSti0HsDPO/tGuXDPgxpbqNb+ccQRqhFcIEIxHV+pP7lINy9HSpcWi1JZdDmnzZJgc6aTvEmfkaJH3hEIHuoJITJD7jECbz8V5E5L+Rj0Io6w5cD2oPZqkjnHqQd5ttU2pwPyVxqH9Vd7GLA="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"学生服务","FUNCTION_TYPE":"1","FUNCTION_ID":"20150116","UNCHANGE":"0","CLASS_NAME":"yh.app.function.WisdomClass","FUNCTION_NAME":"智慧课堂","TYPE_ID":"2","IOS_NOTICE":"segueForWisdomClass","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/zhkt.png","PX":"9","INTEGRATE_KEY":["ZWNBctvJwKoMBsBunHKoCVJ2Rz7VqxbB1eSi5wJ4LZslFjQiXv14CiHoJF4MuO3sjCUMBfu1ozQlkIGDPWKqcS51+mnvBz0F7b1J/R9hNhQlR77uHcQwpJxLYOnhgL0/VuCU3p6TcGDyFMAPVNvmr5+o0X6huC7gVegsLURZob0="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"学生服务","FUNCTION_TYPE":"2","FUNCTION_ID":"20150115","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"问卷调查","TYPE_ID":"2","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/wjdc.png","PX":"26","INTEGRATE_KEY":["gXmjSMi018zAo4Ox08V7x0LaYVJ6TcqlohH6+m0ybPI+aIfEH3jqXl1uUub5pVjU93TaSp9nW7k5W07pHnfA3vp2+qE9jEq0PQ3LFQgvROqnuogE7av4LyYxnY0gpw9VsToaPXyg2Dz2geGS2y3HSeyE1dd4WedlfZ7CJEGye3Q="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"学生服务","FUNCTION_TYPE":"2","FUNCTION_ID":"20150122","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"掌上迎新","TYPE_ID":"2","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/zsyx.png","PX":"27","INTEGRATE_KEY":["ZgyrhhmtigA3LM/RroRKInS5Sv2FUt1Fqw0J/Ie6b0Qfi2QD2TF6roAD+fMV1CAknrLILgS8N2Mw5bnbLncmfc8wGubKpaMWM8octrkio23V0ojtvih12KdvdBvsTJFXhbFb0BiOlvRxsmnSPnvgd/UqiFSKSs0Q4OfvGzs24aA="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"学生服务","FUNCTION_TYPE":"2","FUNCTION_ID":"30000103","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"迎新统计","TYPE_ID":"2","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/yxtj.png","PX":"28","INTEGRATE_KEY":["ZgyrhhmtigA3LM/RroRKInS5Sv2FUt1Fqw0J/Ie6b0Qfi2QD2TF6roAD+fMV1CAknrLILgS8N2Mw5bnbLncmfc8wGubKpaMWM8octrkio23V0ojtvih12KdvdBvsTJFXhbFb0BiOlvRxsmnSPnvgd/UqiFSKSs0Q4OfvGzs24aA="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"}]},{"NAME":"生活服务","LIST":[{"TYPE_NAME":"生活服务","FUNCTION_TYPE":"2","FUNCTION_ID":"20150125","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"学校概况","TYPE_ID":"3","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/xygk.png","PX":"1","INTEGRATE_KEY":["N/OFJzMSSUODjNUyft6e6u2YekOOVJyDSrqT/EdaWbDy7XfTBfaSyxGzQ4+FliALyyZmWM+WvHHwxfNy8S2ACT1/7jCeh7blYO+jPRP9iyBIJx4CYwWRNA+1g/ULcctzxjiJG2BUpxtIDoEJL5IERnfmt9GEiw00rFpIJSQK23s="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"生活服务","FUNCTION_TYPE":"2","FUNCTION_ID":"30000102","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"掌上离校单","TYPE_ID":"3","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/zslxd.png","PX":"5","INTEGRATE_KEY":["WdaLkH0SfH9Z5F/s7QV0eNkF45InQTqS6iql/Bys6jaxL+9gYaM55QcJYe0uYV9xocQmRXnY9P5P53Ke7TY7H3uYJwkvBDTWQ84KpxeAiBPTFD8hpgsP2hVbZMHhk6rceRrlOewTNkQpRpFMRRXRUApkpMpGuLQKh1gU0bzyBhA="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"生活服务","FUNCTION_TYPE":"2","FUNCTION_ID":"20160224","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"一卡通","TYPE_ID":"3","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/xykcx.png","PX":"7","INTEGRATE_KEY":["PsVos96gESyloaqNAYkVlNXp1V2JSqhcB3H4svyqrKGGwHbBXJfwpmQ2wbk2nNCpqGEmrSbi8BdQpiR1mjO8XVtXYICDU/hMFmKaIFCiszwuSk0fSyTXqa+amcpyPgXyVI3106mhlX87lDSna3PylLSyi/8+ZxCqAIvtXr5p8wY="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"生活服务","FUNCTION_TYPE":"1","FUNCTION_ID":"20150101","UNCHANGE":"0","CLASS_NAME":"yh.app.calendar.Canlendar","FUNCTION_NAME":"校历日程","TYPE_ID":"3","IOS_NOTICE":"segueForCalendar","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/xlrc.png","PX":"12","INTEGRATE_KEY":["mH+VFvVCamymEkxdOYtItuVCx8o5y8QmmpySGRHlQp/ImSfsVhP6McRztaIREdTQaOMyeZi3rnFC5jXANfQ90jRjJzYcS97qMC8gZ9VMcEyJGKBNNybeC8XEA0pzFFTd8Mb+fFCTufmg2M92GW+Ub62GTJ38alKwG7SkM56ludU="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"生活服务","FUNCTION_TYPE":"1","FUNCTION_ID":"20150124","UNCHANGE":"0","CLASS_NAME":"yh.app.function.CampusMap","FUNCTION_NAME":"校内地图","TYPE_ID":"3","IOS_NOTICE":"segueForMap","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/xndt.png","PX":"15","INTEGRATE_KEY":["iI6qtjC5zB5+qR8fIIBXl7TN6ojFvHl24FIoXieavOhgiFkXU08e6o4is2UnGBF5Q7dlEwKcCp6Hw94tjU+kL+hbTrjtbroGkQthxstKcdjt8/tTBumaA78WvSLiel+iuCGbZ/wV9ojQ1cbTH8KSj8S5NkUcCUvHsHcggzRtHCE="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"生活服务","FUNCTION_TYPE":"2","FUNCTION_ID":"30000011","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"电子邮件","TYPE_ID":"3","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/dzyj.png","PX":"40","INTEGRATE_KEY":["H/LeZWtrYkbOgwQIELyEUajFi61wdLiccmKXz3oNsmxd1D1dUV10rkBcUJ4xLr5ySVtciJ8KXrM6hTo1OiQCT6QPANwosYFtpgYJgQybJrH10Z78wyy9e3jJ6tZ9paQ7542/USImTRxCBYiRTW4W3GLCkf+W+fjG3e1WvUtMZK0="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"}]},{"NAME":"其他应用","LIST":[{"TYPE_NAME":"其他应用","FUNCTION_TYPE":"2","FUNCTION_ID":"20150103","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"学校通知","TYPE_ID":"4","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/tzgg.png","PX":"6","INTEGRATE_KEY":["F2LkhIAR7NB5iBPwTT3utw9IpsXCLAJo57oDwfClb55nhCjTO7H983QCYb4mzafrE2vQ1WvbeRGa4cZzRfX8Of6VRfMkzKbkUFsIcpsgG4yPyp9y6DlkbqN4PySlw74zQgIngmm3CuB/OmAwDTlFbYhyzJTszCc/lXlf0EmkCG0="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"其他应用","FUNCTION_TYPE":"2","FUNCTION_ID":"20150109","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"班车查询","TYPE_ID":"4","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/bccx.png","PX":"17","INTEGRATE_KEY":["o9mTK3nWRgJIgocA7vPYSxC0esVqGzB6VlbhYCSvXa5OVP+VhDc/78jqxKPso8onIvgKnSAnVvTdy3WM0AR98HpoWAjxjcmNtC3GvbX0k8RlhJe5o06nlOzbKzErSl4dK7eC17f5f0GX2Dy2NmRysvqd+WdxyK+6aSjgo/QrZwQ="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"其他应用","FUNCTION_TYPE":"2","FUNCTION_ID":"30000002","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"办公电话","TYPE_ID":"4","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/bgdhcx.png","PX":"21","INTEGRATE_KEY":["hcpRg2tV4kTXYimRoR4u43btN78G8Sr3Um+uAej/W8aJdzsqXo9MwdWGf+CUD6gnJiTDQOvZjpIaXE6wK1uvI6a0DIJEQXwlJq0hmS5MVGo1ch7m+wZsFoIHqAtu6Qzc4JiRF+dhNBqOmSVUQH6hlGXfHIPe7Z5fSOxZ7mNcJGQ="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"其他应用","FUNCTION_TYPE":"2","FUNCTION_ID":"30000109","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"数字资源","TYPE_ID":"4","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/szzy.png","PX":"22","INTEGRATE_KEY":["FI8/s4WzsvNPAiKUUGg41uE7G4w5LtLMtnRcMu2GHFSh3ZWiA5AUo7/7dFMXFtVgWfYXhlNeRGG51UXZVw9m612Mlc2or62wQHBEh9+dY3+kHMIliwNE+eN02ayg+4/m+bqIvWLerMdfle/Fvu74p6DTfcy4tHKhav3Pi8+6hLI="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"其他应用","FUNCTION_TYPE":"1","FUNCTION_ID":"30000108","UNCHANGE":"0","CLASS_NAME":"yh.sina.weibo.sdk.demo.WBAuthActivity","FUNCTION_NAME":"微博聚合","TYPE_ID":"4","IOS_NOTICE":"WeiboPolymerization","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/wbjh.png","PX":"22","INTEGRATE_KEY":["hufpiKOBtbY7HueZs3F4XtInQLgiCbRE/mzRgcF8I2WoNtBRv26eeHMgcT+yCk5flEAN6IWo4A9pRCDE67qYfigD/a6wGX5J/DrRPDJ3iafNxuvegv5L15uCDP65cpXP4nqlOcIAxKgaLdtlxSRdYGwZREnSQXDCZpCMIZ2aCTk="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"}]}]
     * MESSAGE_APP : [{"NAME":"学生服务","LIST":[{"TYPE_NAME":"学生服务","FUNCTION_TYPE":"2","FUNCTION_ID":"30000010","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"个人事务","TYPE_ID":"2","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/push_zsjy.png","PX":"6","INTEGRATE_KEY":["LoEcPWpsREaYYNMV02C369vCeyWT2+LPw+m5t1GTmU6HXOaQQLyCmr2tGWjfbAEQrDwxj9gmprrwRQ0aXvwO86H6Q1ZQkwYDuOKi2scs9OqrwDCVsMObSE26MnQWmu3piErziBHNjjahaUgXgDcpMHUpJH7b8VQh9/Wu+jyNDU4="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"1"}]},{"NAME":"生活服务","LIST":[{"TYPE_NAME":"生活服务","FUNCTION_TYPE":"2","FUNCTION_ID":"30000008","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"就业信息","TYPE_ID":"3","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/xwpt.png","PX":"4","INTEGRATE_KEY":["LoEcPWpsREaYYNMV02C369vCeyWT2+LPw+m5t1GTmU6HXOaQQLyCmr2tGWjfbAEQrDwxj9gmprrwRQ0aXvwO86H6Q1ZQkwYDuOKi2scs9OqrwDCVsMObSE26MnQWmu3piErziBHNjjahaUgXgDcpMHUpJH7b8VQh9/Wu+jyNDU4="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"1"}]},{"NAME":"其他应用","LIST":[{"TYPE_NAME":"其他应用","FUNCTION_TYPE":"2","FUNCTION_ID":"20160225","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"行政事务","TYPE_ID":"4","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/xysc.png","PX":"1","INTEGRATE_KEY":["LoEcPWpsREaYYNMV02C369vCeyWT2+LPw+m5t1GTmU6HXOaQQLyCmr2tGWjfbAEQrDwxj9gmprrwRQ0aXvwO86H6Q1ZQkwYDuOKi2scs9OqrwDCVsMObSE26MnQWmu3piErziBHNjjahaUgXgDcpMHUpJH7b8VQh9/Wu+jyNDU4="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"1"},{"TYPE_NAME":"其他应用","FUNCTION_TYPE":"2","FUNCTION_ID":"30000007","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"教学通知","TYPE_ID":"4","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/xwpt.png","PX":"3","INTEGRATE_KEY":["LoEcPWpsREaYYNMV02C369vCeyWT2+LPw+m5t1GTmU6HXOaQQLyCmr2tGWjfbAEQrDwxj9gmprrwRQ0aXvwO86H6Q1ZQkwYDuOKi2scs9OqrwDCVsMObSE26MnQWmu3piErziBHNjjahaUgXgDcpMHUpJH7b8VQh9/Wu+jyNDU4="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"1"}]}]
     * FUNCTION_VERSION : 10
     */

    private String FUNCTION_VERSION;
    private List<OTHERAPPBean> OTHER_APP;
    private List<MESSAGEAPPBean> MESSAGE_APP;

    public String getFUNCTION_VERSION() {
        return FUNCTION_VERSION;
    }

    public void setFUNCTION_VERSION(String FUNCTION_VERSION) {
        this.FUNCTION_VERSION = FUNCTION_VERSION;
    }

    public List<OTHERAPPBean> getOTHER_APP() {
        return OTHER_APP;
    }

    public void setOTHER_APP(List<OTHERAPPBean> OTHER_APP) {
        this.OTHER_APP = OTHER_APP;
    }

    public List<MESSAGEAPPBean> getMESSAGE_APP() {
        return MESSAGE_APP;
    }

    public void setMESSAGE_APP(List<MESSAGEAPPBean> MESSAGE_APP) {
        this.MESSAGE_APP = MESSAGE_APP;
    }

    public static class OTHERAPPBean {
        /**
         * NAME : 教学服务
         * LIST : [{"TYPE_NAME":"教学服务","FUNCTION_TYPE":"1","FUNCTION_ID":"20150120","UNCHANGE":"1","CLASS_NAME":"yh.app.function.FriendCycle","FUNCTION_NAME":"社区","TYPE_ID":"1","IOS_NOTICE":"segueForCircle","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/qz.png","PX":"4","INTEGRATE_KEY":["GbUdyvrrJ7pW8QP9luabi7JodC7cJtnJfQl5JU4G+vua2mTdCpXRYgNgDBLdgIGiNo9aN7sr+JTPt8qPSCe2LslsfrG5w5StXcM6KO2VyPkMNJXnYswF4WnLQRzmItUBuJJD5qaP2/bKH9bMhvpUN3NO0iaSu5HTUZsuclqyGzQ="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"教学服务","FUNCTION_TYPE":"2","FUNCTION_ID":"20150112","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"校园实景","TYPE_ID":"1","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/xysj.png","PX":"7","INTEGRATE_KEY":["nhjsbgeiR15DTidb/19oYxNwiegwmqWzF2h83T8ZSqJqNZmqcMDLJmFXfxEdgyy2gWtllicMG0l5vEkPubh1idl1VIMqlrYhHZ3IRYjvBoSHT60W24PL2zuXlF0gSB0deGMmmvFzd9fn++yEa0+Oqdy2xt9kMy7kXNmagm64/DA="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"教学服务","FUNCTION_TYPE":"1","FUNCTION_ID":"20150105","UNCHANGE":"0","CLASS_NAME":"yh.app.coursetable.TableDemoActivity","FUNCTION_NAME":"课表查询","TYPE_ID":"1","IOS_NOTICE":"segueForCourse","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/kbcx.png","PX":"10","INTEGRATE_KEY":["ikrEb2w/Sb7Aoxs89zZzRWxwby3gk9UqkDQqDJWKud47MYIP1MCTfuxKeApx7phjor4I4/Q3+nebMgfMAqmaoNr127ppcsGfnVoV2yH5G5699xdAqHcDpgndBjnMoiem85jGLWj24X7OLX1tzYayWr5Fx3mVZIJ/8mwTPIHorJA="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"教学服务","FUNCTION_TYPE":"2","FUNCTION_ID":"30000101","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"公文处理","TYPE_ID":"1","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/oabg.png","PX":"15","INTEGRATE_KEY":["RxZMtLhdd5EVz0N2pwK6sB/zweSxha9+pJtGLixOyyQnmkXkCdcNhcUXYb38F7TmLNoVPV7ayU1sDosIIuZ2r2ZC63m6+WS01HYu+b5Myb20rIM/WMgBmL5qca/c0ODB6cCIj81co/tFqRPTRDXMmM0j9o9EgFZH1tbAB/pECX4="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"教学服务","FUNCTION_TYPE":"2","FUNCTION_ID":"20150113","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"财务查询","TYPE_ID":"1","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/gzjf.png","PX":"19","INTEGRATE_KEY":["NJbP3A4HiKQWkAvAOz6cMbE1CMuc0ie2UuTIzVtFf2vj9mL0izKQls56AE4b8RkRhJPMUGpyIHMZ5h0KPpNFJdMagrMjd5NreizcT1hXK+7fd/Tr5J4A+Gq8sPN6RxltElQo9LFnPev8gjhPC4scSec08sUpUB5mXR6i01we+qc="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"教学服务","FUNCTION_TYPE":"2","FUNCTION_ID":"30000003","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"毕业审核","TYPE_ID":"1","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/bysh.png","PX":"40","INTEGRATE_KEY":["RxZMtLhdd5EVz0N2pwK6sB/zweSxha9+pJtGLixOyyQnmkXkCdcNhcUXYb38F7TmLNoVPV7ayU1sDosIIuZ2r2ZC63m6+WS01HYu+b5Myb20rIM/WMgBmL5qca/c0ODB6cCIj81co/tFqRPTRDXMmM0j9o9EgFZH1tbAB/pECX4="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"教学服务","FUNCTION_TYPE":"2","FUNCTION_ID":"30000006","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"监考查询","TYPE_ID":"1","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/jkcx.png","PX":"40","INTEGRATE_KEY":["nyndUaYs+kbs94yhVw2aUgsGWbVSTOh1/BM2W+f+Gw3sJLGpsCP5rceTHvkXxiM26QlyBeAl+HANzltKT9IOxeSnsU+KUX7TjiaMlRuvkWgY/5ENgKx2fBBYcOOam9WbXtU5HuGoqr0XKxvs4BZEEat6R+BaI6B3z85TQr1ilKg="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"},{"TYPE_NAME":"教学服务","FUNCTION_TYPE":"2","FUNCTION_ID":"30000005","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"考试查询","TYPE_ID":"1","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/kscx.png","PX":"40","INTEGRATE_KEY":["nyndUaYs+kbs94yhVw2aUgsGWbVSTOh1/BM2W+f+Gw3sJLGpsCP5rceTHvkXxiM26QlyBeAl+HANzltKT9IOxeSnsU+KUX7TjiaMlRuvkWgY/5ENgKx2fBBYcOOam9WbXtU5HuGoqr0XKxvs4BZEEat6R+BaI6B3z85TQr1ilKg="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"0"}]
         */

        private String NAME;
        private List<LISTBean> LIST;

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public List<LISTBean> getLIST() {
            return LIST;
        }

        public void setLIST(List<LISTBean> LIST) {
            this.LIST = LIST;
        }

        public static class LISTBean {
            /**
             * TYPE_NAME : 教学服务
             * FUNCTION_TYPE : 1
             * FUNCTION_ID : 20150120
             * UNCHANGE : 1
             * CLASS_NAME : yh.app.function.FriendCycle
             * FUNCTION_NAME : 社区
             * TYPE_ID : 1
             * IOS_NOTICE : segueForCircle
             * FUNCTION_FACE : http://app.cqut.edu.cn/content/image/qz.png
             * PX : 4
             * INTEGRATE_KEY : ["GbUdyvrrJ7pW8QP9luabi7JodC7cJtnJfQl5JU4G+vua2mTdCpXRYgNgDBLdgIGiNo9aN7sr+JTPt8qPSCe2LslsfrG5w5StXcM6KO2VyPkMNJXnYswF4WnLQRzmItUBuJJD5qaP2/bKH9bMhvpUN3NO0iaSu5HTUZsuclqyGzQ="]
             * PACKAGE_NAME : yh.app.appstart.lg
             * FUNCTION_TYBJ : 0
             */

            private String TYPE_NAME;
            private String FUNCTION_TYPE;
            private String FUNCTION_ID;
            private String UNCHANGE;
            private String CLASS_NAME;
            private String FUNCTION_NAME;
            private String TYPE_ID;
            private String IOS_NOTICE;
            private String FUNCTION_FACE;
            private String PX;
            private String PACKAGE_NAME;
            private String FUNCTION_TYBJ;
            private List<String> INTEGRATE_KEY;

            public String getTYPE_NAME() {
                return TYPE_NAME;
            }

            public void setTYPE_NAME(String TYPE_NAME) {
                this.TYPE_NAME = TYPE_NAME;
            }

            public String getFUNCTION_TYPE() {
                return FUNCTION_TYPE;
            }

            public void setFUNCTION_TYPE(String FUNCTION_TYPE) {
                this.FUNCTION_TYPE = FUNCTION_TYPE;
            }

            public String getFUNCTION_ID() {
                return FUNCTION_ID;
            }

            public void setFUNCTION_ID(String FUNCTION_ID) {
                this.FUNCTION_ID = FUNCTION_ID;
            }

            public String getUNCHANGE() {
                return UNCHANGE;
            }

            public void setUNCHANGE(String UNCHANGE) {
                this.UNCHANGE = UNCHANGE;
            }

            public String getCLASS_NAME() {
                return CLASS_NAME;
            }

            public void setCLASS_NAME(String CLASS_NAME) {
                this.CLASS_NAME = CLASS_NAME;
            }

            public String getFUNCTION_NAME() {
                return FUNCTION_NAME;
            }

            public void setFUNCTION_NAME(String FUNCTION_NAME) {
                this.FUNCTION_NAME = FUNCTION_NAME;
            }

            public String getTYPE_ID() {
                return TYPE_ID;
            }

            public void setTYPE_ID(String TYPE_ID) {
                this.TYPE_ID = TYPE_ID;
            }

            public String getIOS_NOTICE() {
                return IOS_NOTICE;
            }

            public void setIOS_NOTICE(String IOS_NOTICE) {
                this.IOS_NOTICE = IOS_NOTICE;
            }

            public String getFUNCTION_FACE() {
                return FUNCTION_FACE;
            }

            public void setFUNCTION_FACE(String FUNCTION_FACE) {
                this.FUNCTION_FACE = FUNCTION_FACE;
            }

            public String getPX() {
                return PX;
            }

            public void setPX(String PX) {
                this.PX = PX;
            }

            public String getPACKAGE_NAME() {
                return PACKAGE_NAME;
            }

            public void setPACKAGE_NAME(String PACKAGE_NAME) {
                this.PACKAGE_NAME = PACKAGE_NAME;
            }

            public String getFUNCTION_TYBJ() {
                return FUNCTION_TYBJ;
            }

            public void setFUNCTION_TYBJ(String FUNCTION_TYBJ) {
                this.FUNCTION_TYBJ = FUNCTION_TYBJ;
            }

            public List<String> getINTEGRATE_KEY() {
                return INTEGRATE_KEY;
            }

            public void setINTEGRATE_KEY(List<String> INTEGRATE_KEY) {
                this.INTEGRATE_KEY = INTEGRATE_KEY;
            }
        }
    }

    public static class MESSAGEAPPBean {
        /**
         * NAME : 学生服务
         * LIST : [{"TYPE_NAME":"学生服务","FUNCTION_TYPE":"2","FUNCTION_ID":"30000010","UNCHANGE":"0","CLASS_NAME":"yh.app.web.Web","FUNCTION_NAME":"个人事务","TYPE_ID":"2","IOS_NOTICE":"segueForWebView","FUNCTION_FACE":"http://app.cqut.edu.cn/content/image/push_zsjy.png","PX":"6","INTEGRATE_KEY":["LoEcPWpsREaYYNMV02C369vCeyWT2+LPw+m5t1GTmU6HXOaQQLyCmr2tGWjfbAEQrDwxj9gmprrwRQ0aXvwO86H6Q1ZQkwYDuOKi2scs9OqrwDCVsMObSE26MnQWmu3piErziBHNjjahaUgXgDcpMHUpJH7b8VQh9/Wu+jyNDU4="],"PACKAGE_NAME":"yh.app.appstart.lg","FUNCTION_TYBJ":"1"}]
         */

        private String NAME;
        private List<LISTBeanX> LIST;

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public List<LISTBeanX> getLIST() {
            return LIST;
        }

        public void setLIST(List<LISTBeanX> LIST) {
            this.LIST = LIST;
        }

        public static class LISTBeanX {
            /**
             * TYPE_NAME : 学生服务
             * FUNCTION_TYPE : 2
             * FUNCTION_ID : 30000010
             * UNCHANGE : 0
             * CLASS_NAME : yh.app.web.Web
             * FUNCTION_NAME : 个人事务
             * TYPE_ID : 2
             * IOS_NOTICE : segueForWebView
             * FUNCTION_FACE : http://app.cqut.edu.cn/content/image/push_zsjy.png
             * PX : 6
             * INTEGRATE_KEY : ["LoEcPWpsREaYYNMV02C369vCeyWT2+LPw+m5t1GTmU6HXOaQQLyCmr2tGWjfbAEQrDwxj9gmprrwRQ0aXvwO86H6Q1ZQkwYDuOKi2scs9OqrwDCVsMObSE26MnQWmu3piErziBHNjjahaUgXgDcpMHUpJH7b8VQh9/Wu+jyNDU4="]
             * PACKAGE_NAME : yh.app.appstart.lg
             * FUNCTION_TYBJ : 1
             */

            private String TYPE_NAME;
            private String FUNCTION_TYPE;
            private String FUNCTION_ID;
            private String UNCHANGE;
            private String CLASS_NAME;
            private String FUNCTION_NAME;
            private String TYPE_ID;
            private String IOS_NOTICE;
            private String FUNCTION_FACE;
            private String PX;
            private String PACKAGE_NAME;
            private String FUNCTION_TYBJ;
            private List<String> INTEGRATE_KEY;

            public String getTYPE_NAME() {
                return TYPE_NAME;
            }

            public void setTYPE_NAME(String TYPE_NAME) {
                this.TYPE_NAME = TYPE_NAME;
            }

            public String getFUNCTION_TYPE() {
                return FUNCTION_TYPE;
            }

            public void setFUNCTION_TYPE(String FUNCTION_TYPE) {
                this.FUNCTION_TYPE = FUNCTION_TYPE;
            }

            public String getFUNCTION_ID() {
                return FUNCTION_ID;
            }

            public void setFUNCTION_ID(String FUNCTION_ID) {
                this.FUNCTION_ID = FUNCTION_ID;
            }

            public String getUNCHANGE() {
                return UNCHANGE;
            }

            public void setUNCHANGE(String UNCHANGE) {
                this.UNCHANGE = UNCHANGE;
            }

            public String getCLASS_NAME() {
                return CLASS_NAME;
            }

            public void setCLASS_NAME(String CLASS_NAME) {
                this.CLASS_NAME = CLASS_NAME;
            }

            public String getFUNCTION_NAME() {
                return FUNCTION_NAME;
            }

            public void setFUNCTION_NAME(String FUNCTION_NAME) {
                this.FUNCTION_NAME = FUNCTION_NAME;
            }

            public String getTYPE_ID() {
                return TYPE_ID;
            }

            public void setTYPE_ID(String TYPE_ID) {
                this.TYPE_ID = TYPE_ID;
            }

            public String getIOS_NOTICE() {
                return IOS_NOTICE;
            }

            public void setIOS_NOTICE(String IOS_NOTICE) {
                this.IOS_NOTICE = IOS_NOTICE;
            }

            public String getFUNCTION_FACE() {
                return FUNCTION_FACE;
            }

            public void setFUNCTION_FACE(String FUNCTION_FACE) {
                this.FUNCTION_FACE = FUNCTION_FACE;
            }

            public String getPX() {
                return PX;
            }

            public void setPX(String PX) {
                this.PX = PX;
            }

            public String getPACKAGE_NAME() {
                return PACKAGE_NAME;
            }

            public void setPACKAGE_NAME(String PACKAGE_NAME) {
                this.PACKAGE_NAME = PACKAGE_NAME;
            }

            public String getFUNCTION_TYBJ() {
                return FUNCTION_TYBJ;
            }

            public void setFUNCTION_TYBJ(String FUNCTION_TYBJ) {
                this.FUNCTION_TYBJ = FUNCTION_TYBJ;
            }

            public List<String> getINTEGRATE_KEY() {
                return INTEGRATE_KEY;
            }

            public void setINTEGRATE_KEY(List<String> INTEGRATE_KEY) {
                this.INTEGRATE_KEY = INTEGRATE_KEY;
            }
        }
    }
}
