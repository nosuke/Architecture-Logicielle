package ca.ulaval.glo4003.architecture_logicielle.model;

public class TaskEntry {
	private Integer id;
	private String name;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object object) {
		return (this.id == ((TaskEntry) object).getId() &&
				this.name == ((TaskEntry) object).getName());
	}
}
