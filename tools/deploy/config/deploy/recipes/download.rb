module Giovanni::Plugins::Nexus
  # Returns the filename of a WAR file, handling SNAPSHOT versions
  def filename
    "#{artifact_id}.#{packaging}"
  end

  def folder
    "#{repository}"
  end
end