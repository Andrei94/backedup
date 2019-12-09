package uploader;

import file.LocalFile;

import java.util.stream.Stream;

class LocalFileWalkerStub extends LocalFileWalker {
	private LocalFile file;

	void setFile(LocalFile file) {
		this.file = file;
	}

	@Override
	protected Stream<LocalFile> walkTreeFromRoot(LocalFile root) {
		return Stream.of(file);
	}
}
