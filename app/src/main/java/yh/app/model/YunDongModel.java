package yh.app.model;

public class YunDongModel {
	/**
	 * content : {"message":{"djz":"第10周","xqj":"星期四","rq":"2017-04-27","bz":"0","numberofnow":"2341","sportNumber":"7000","title":"【温馨提示】","describe":"体育锻炼有利于人体骨骼、肌肉的生长，增强心肺功能，改善血液循环系统、呼吸系统和消化系统的机能状况，有利于人体的生长发育，提高抗病能力，增强有机体的适应能力。体育锻炼具有调节人体紧张情绪的作用，能改善生理和心理状态，恢复体力和精力；增进身体健康，使疲劳的身体得到积极的休息，使人精力充沛地投入学习、工作。"},"ticket":"f8902433-ce51-4e4a-bff7-06bcb20fc2d5","status":"false","code":"60008"}
	 * message : 成功
	 * code : 40001
	 */

	private ContentBean content;
	private String message;
	private String code;

	public ContentBean getContent() {
		return content;
	}

	public void setContent(ContentBean content) {
		this.content = content;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static class ContentBean {
		/**
		 * message : {"djz":"第10周","xqj":"星期四","rq":"2017-04-27","bz":"0","numberofnow":"2341","sportNumber":"7000","title":"【温馨提示】","describe":"体育锻炼有利于人体骨骼、肌肉的生长，增强心肺功能，改善血液循环系统、呼吸系统和消化系统的机能状况，有利于人体的生长发育，提高抗病能力，增强有机体的适应能力。体育锻炼具有调节人体紧张情绪的作用，能改善生理和心理状态，恢复体力和精力；增进身体健康，使疲劳的身体得到积极的休息，使人精力充沛地投入学习、工作。"}
		 * ticket : f8902433-ce51-4e4a-bff7-06bcb20fc2d5
		 * status : false
		 * code : 60008
		 */

		private MessageBean message;
		private String ticket;
		private String status;
		private String code;

		public MessageBean getMessage() {
			return message;
		}

		public void setMessage(MessageBean message) {
			this.message = message;
		}

		public String getTicket() {
			return ticket;
		}

		public void setTicket(String ticket) {
			this.ticket = ticket;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public static class MessageBean {
			/**
			 * djz : 第10周
			 * xqj : 星期四
			 * rq : 2017-04-27
			 * bz : 0
			 * numberofnow : 2341
			 * sportNumber : 7000
			 * title : 【温馨提示】
			 * describe : 体育锻炼有利于人体骨骼、肌肉的生长，增强心肺功能，改善血液循环系统、呼吸系统和消化系统的机能状况，有利于人体的生长发育，提高抗病能力，增强有机体的适应能力。体育锻炼具有调节人体紧张情绪的作用，能改善生理和心理状态，恢复体力和精力；增进身体健康，使疲劳的身体得到积极的休息，使人精力充沛地投入学习、工作。
			 */

			private String djz;
			private String xqj;
			private String rq;
			private String bz;
			private String numberofnow;
			private String sportNumber;
			private String title;
			private String describe;

			public String getDjz() {
				return djz;
			}

			public void setDjz(String djz) {
				this.djz = djz;
			}

			public String getXqj() {
				return xqj;
			}

			public void setXqj(String xqj) {
				this.xqj = xqj;
			}

			public String getRq() {
				return rq;
			}

			public void setRq(String rq) {
				this.rq = rq;
			}

			public String getBz() {
				return bz;
			}

			public void setBz(String bz) {
				this.bz = bz;
			}

			public String getNumberofnow() {
				return numberofnow;
			}

			public void setNumberofnow(String numberofnow) {
				this.numberofnow = numberofnow;
			}

			public String getSportNumber() {
				return sportNumber;
			}

			public void setSportNumber(String sportNumber) {
				this.sportNumber = sportNumber;
			}

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public String getDescribe() {
				return describe;
			}

			public void setDescribe(String describe) {
				this.describe = describe;
			}
		}
	}
}
