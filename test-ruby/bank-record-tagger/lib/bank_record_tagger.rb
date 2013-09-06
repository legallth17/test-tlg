class BankRecordTagger

  def initialize
    @tag_groups = Hash.new{|hash, key| hash[key] = Hash.new } 
  end

  def get_tags(description)
    tags=[]
    @tag_groups.each do |group,rules|
      if group == "default" then
        tags = tags + get_all_tags_that_match(description, rules)
      else
        tag = get_tag_that_best_matches(description, rules)
        tags = tags << tag if tag != nil
      end
    end
    return tags
  end

  def get_all_tags_that_match(description, rules)
    tags=[]
    rules.each do |pattern,tag|
      regex = Regexp.new(pattern)
      tags.push(tag) if regex =~ description
    end
    return tags
  end

  def get_tag_that_best_matches(description, rules)
    best_size = 0
    best_tag = nil
    rules.each do |pattern,tag|
      regex = Regexp.new(pattern)
      match = regex.match(description) 
      if match != nil then
        match_size = match.to_s.length
        if match_size > best_size then
          best_size, best_tag = match_size, tag
        end
      end
    end
    return best_tag
  end

  def add_rule(pattern, tag, tag_group = "default")
    rules = @tag_groups[tag_group]
    rules[pattern] = tag
  end

end