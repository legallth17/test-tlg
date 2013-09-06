class BankRecordTagger

  attr_reader :rules
  
  def initialize
    @rules = Hash.new
  end

  def get_tags(description)
    tags=[]
    @rules.each do |pattern,tag|
      regex = Regexp.new(pattern)
      tags.push(tag) if regex =~ description
    end
    return tags
  end

  def add_rule(pattern, tag)
    @rules[pattern] = tag
  end

end