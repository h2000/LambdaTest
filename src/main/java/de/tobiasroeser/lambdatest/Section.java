package de.tobiasroeser.lambdatest;

public class Section {

	private final String name;
	private final Section parent;
	private final int level;

	public Section(String name, Section parent) {
		this.name = name;
		this.parent = parent;
		this.level = 1 + (parent == null ? 0 : parent.getLevel());
	}

	public String getName() {
		return name;
	}

	public Section getParent() {
		return parent;
	}

	public int getLevel() {
		return level;
	}

	@Override
	public String toString() {
		return name;
	}

	public Optional<Section> findInParents(Section section) {
		if (section == null) {
			return Optional.none();
		} else if (this.equals(section)) {
			return Optional.some(this);
		} else if (parent != null) {
			return parent.findInParents(section);
		}
		return Optional.none();
	}

}